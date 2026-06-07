package com.shetty.pagination.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BusinessesDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("alias") var alias: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("is_closed") var isClosed: Boolean? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("review_count") var reviewCount: Int? = null,
    @SerializedName("categories") var categories: ArrayList<CategoriesDto> = arrayListOf(),
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("coordinates") var coordinates: CoordinatesDto? = CoordinatesDto(),
    @SerializedName("transactions") var transactions: ArrayList<String> = arrayListOf(),
    @SerializedName("price") var price: String? = null,
    @SerializedName("location") var location: LocationDto? = LocationDto(),
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("display_phone") var displayPhone: String? = null,
    @SerializedName("distance") var distance: Double? = null
)
