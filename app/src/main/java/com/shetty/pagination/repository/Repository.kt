package com.shetty.pagination.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shetty.pagination.db.AppDatabase
import com.shetty.pagination.db.RestaurantEntity
import com.shetty.pagination.network.INetwork
import com.shetty.pagination.paging.RestaurantRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val iNetwork: INetwork,
    private val appDatabase: AppDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getNearbyRestaurants(radius: Int): Flow<PagingData<RestaurantEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            remoteMediator = RestaurantRemoteMediator(
                iNetwork = iNetwork,
                appDatabase = appDatabase,
                radius = radius
            ),
            pagingSourceFactory = {
                appDatabase.restaurantDao().getRestaurantsByRadius(radius)
            }
        ).flow
    }
}
