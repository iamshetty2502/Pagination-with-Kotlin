package com.shetty.pagination.presentation.state

sealed class MainEvent {
    data class UpdateRadius(val radius: Int) : MainEvent()
}
