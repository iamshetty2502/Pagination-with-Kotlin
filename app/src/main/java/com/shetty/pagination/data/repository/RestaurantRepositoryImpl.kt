package com.shetty.pagination.data.repository

import androidx.paging.*
import com.shetty.pagination.data.mapper.toDomain
import com.shetty.pagination.db.AppDatabase
import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.domain.repository.RestaurantRepository
import com.shetty.pagination.network.INetwork
import com.shetty.pagination.paging.RestaurantRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val iNetwork: INetwork,
    private val appDatabase: AppDatabase
) : RestaurantRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNearbyRestaurants(radius: Int): Flow<PagingData<Restaurant>> {
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
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getRestaurantById(id: String): Restaurant? {
        return appDatabase.restaurantDao().getRestaurantById(id)?.toDomain()
    }
}
