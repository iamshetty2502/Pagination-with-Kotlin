package com.shetty.pagination.main

sealed class MainUiState {
    object Idle : MainUiState()
    data class Success(val radius: Int) : MainUiState()
    data class Error(val message: String) : MainUiState()
}
