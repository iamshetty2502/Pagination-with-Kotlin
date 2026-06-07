package com.shetty.pagination.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesDto(
    @SerializedName("alias") var alias: String? = null,
    @SerializedName("title") var title: String? = null
)
