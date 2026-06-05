package com.shetty.pagination.main

sealed class MainEvent {
    data class UpdateRadius(val radius: Int) : MainEvent()
}
