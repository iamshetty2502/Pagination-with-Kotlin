package com.shetty.pagination.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shetty.pagination.data.local.AppDatabase
import com.shetty.pagination.data.local.entity.RemoteKeys
import com.shetty.pagination.data.local.entity.RestaurantEntity
import com.shetty.pagination.data.remote.api.YelpApi
import com.shetty.pagination.utils.Constants

@OptIn(ExperimentalPagingApi::class)
class RestaurantRemoteMediator(
    private val yelpApi: YelpApi,
    private val appDatabase: AppDatabase,
    private val radius: Int
) : RemoteMediator<Int, RestaurantEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RestaurantEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = yelpApi.getNearbyRestaurants(
                Constants.SORT_BY,
                Constants.LOCATION,
                Constants.TERM,
                Constants.LIMIT,
                radius,
                (page - 1) * Constants.LIMIT
            )

            val endOfPaginationReached = response.businesses.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeysByRadius(radius)
                    appDatabase.restaurantDao().clearAllByRadius(radius)
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.businesses.map {
                    RemoteKeys(
                        restaurantId = it.id!!,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        radius = radius
                    )
                }
                appDatabase.remoteKeysDao().insertAll(keys)
                appDatabase.restaurantDao().insertAll(response.businesses.map {
                    RestaurantEntity(
                        id = it.id!!,
                        name = it.name ?: "",
                        imageUrl = it.imageUrl ?: "",
                        address = it.location?.displayAddress?.joinToString(", ") ?: "",
                        isOpen = it.isClosed == false,
                        phone = it.displayPhone ?: "",
                        radius = radius,
                        page = page
                    )
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RestaurantEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { restaurant ->
                appDatabase.remoteKeysDao().getRemoteKeysId(restaurant.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RestaurantEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { restaurant ->
                appDatabase.remoteKeysDao().getRemoteKeysId(restaurant.id)
            }
    }

    private suspend fun getRemoteKeyClosestToPosition(state: PagingState<Int, RestaurantEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}