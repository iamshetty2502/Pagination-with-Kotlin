package com.shetty.pagination.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.shetty.pagination.data.local.AppDatabase
import com.shetty.pagination.data.mapper.toDomain
import com.shetty.pagination.data.remote.api.YelpApi
import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.domain.repository.RestaurantRepository
import com.shetty.pagination.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val yelpApi: YelpApi,
    private val appDatabase: AppDatabase
) : RestaurantRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNearbyRestaurants(radius: Int): Flow<PagingData<Restaurant>> {
        val pagingSourceFactory = { appDatabase.restaurantDao().getRestaurantsByRadius(radius) }

        return Pager(
            config = PagingConfig(
                pageSize = Constants.LIMIT,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            remoteMediator = RestaurantRemoteMediator(
                yelpApi = yelpApi,
                appDatabase = appDatabase,
                radius = radius
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}
