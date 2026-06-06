package com.shetty.pagination.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shetty.pagination.domain.usecase.GetRestaurantByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRestaurantByIdUseCase: GetRestaurantByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getRestaurantDetails(id)
        }
    }

    private fun getRestaurantDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            val restaurant = getRestaurantByIdUseCase(id)
            if (restaurant != null) {
                _uiState.value = DetailUiState.Success(restaurant)
            } else {
                _uiState.value = DetailUiState.Error("Restaurant not found")
            }
        }
    }
}
