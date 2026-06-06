package com.shetty.pagination.data.mapper

import com.shetty.pagination.db.RestaurantEntity
import com.shetty.pagination.domain.model.Restaurant

fun RestaurantEntity.toDomain(): Restaurant {
    return Restaurant(
        id = id,
        name = name,
        imageUrl = imageUrl,
        address = address,
        isOpen = isOpen,
        phone = phone
    )
}
