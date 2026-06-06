package com.shetty.pagination.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val address: String,
    val isOpen: Boolean,
    val phone: String,
    val radius: Int, // Storing radius to filter offline
    val page: Int    // Helpful for ordered retrieval
)
