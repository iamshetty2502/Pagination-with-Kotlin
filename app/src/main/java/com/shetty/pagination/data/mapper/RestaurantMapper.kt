package com.shetty.pagination.data.mapper

import com.shetty.pagination.data.local.entity.RestaurantEntity
import com.shetty.pagination.data.remote.dto.BusinessesDto
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

fun BusinessesDto.toDomain(): Restaurant {
    return Restaurant(
        id = id ?: "",
        name = name ?: "",
        imageUrl = imageUrl ?: "",
        address = location?.displayAddress?.joinToString(", ") ?: "",
        isOpen = isClosed == false,
        phone = displayPhone ?: phone ?: ""
    )
}
