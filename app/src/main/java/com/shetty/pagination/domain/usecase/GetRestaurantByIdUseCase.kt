package com.shetty.pagination.domain.usecase

import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetRestaurantByIdUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(id: String): Restaurant? {
        return repository.getRestaurantById(id)
    }
}
