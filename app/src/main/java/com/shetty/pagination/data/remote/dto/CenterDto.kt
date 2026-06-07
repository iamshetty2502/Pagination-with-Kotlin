package com.shetty.pagination.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CenterDto(
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("latitude") var latitude: Double? = null
)
