package com.shetty.pagination.domain.usecase

import androidx.paging.PagingData
import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke(radius: Int): Flow<PagingData<Restaurant>> {
        return repository.getNearbyRestaurants(radius)
    }
}
