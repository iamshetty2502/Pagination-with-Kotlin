package com.shetty.pagination.domain.repository

import androidx.paging.PagingData
import com.shetty.pagination.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getNearbyRestaurants(radius: Int): Flow<PagingData<Restaurant>>
    suspend fun getRestaurantById(id: String): Restaurant?
}
