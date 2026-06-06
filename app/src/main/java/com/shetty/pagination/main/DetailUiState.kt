package com.shetty.pagination.main

import com.shetty.pagination.domain.model.Restaurant

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val restaurant: Restaurant) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
