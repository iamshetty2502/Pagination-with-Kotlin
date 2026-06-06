package com.shetty.pagination.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val imageUrl: String,
    val address: String,
    val isOpen: Boolean,
    val phone: String
)
