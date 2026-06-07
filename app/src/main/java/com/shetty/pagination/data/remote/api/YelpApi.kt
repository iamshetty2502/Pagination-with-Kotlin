package com.shetty.pagination.data.remote.api

import com.shetty.pagination.data.remote.dto.ResultDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpApi {
    @GET("/v3/businesses/search")
    suspend fun getNearbyRestaurants(
        @Query("sort_by") sortBy: String?,
        @Query("location") location: String?,
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("radius") radius: Int,
        @Query("offset") offset: Int
    ): ResultDataDto
}
