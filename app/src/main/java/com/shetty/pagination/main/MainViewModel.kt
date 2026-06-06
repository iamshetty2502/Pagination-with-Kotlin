package com.shetty.pagination.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.domain.usecase.GetNearbyRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Success(radius = 0))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.UpdateRadius -> {
                _uiState.value = MainUiState.Success(radius = event.radius)
            }
        }
    }

    fun getRestaurantsInProvidedRadius(radius: Int): Flow<PagingData<Restaurant>> {
        return getNearbyRestaurantsUseCase(radius).cachedIn(viewModelScope)
    }
}
