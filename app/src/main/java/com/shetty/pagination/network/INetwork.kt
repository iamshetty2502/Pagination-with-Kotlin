package com.shetty.pagination.network

import com.shetty.pagination.models.ResultData
import retrofit2.http.GET
import retrofit2.http.Query

interface INetwork {
    @GET("/v3/businesses/search")
    suspend fun getNearbyRestaurants(
        @Query("sort_by") sortBy: String?,
        @Query("location") location: String?,
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("radius") radius: Int,
        @Query("offset") offset: Int
    ): ResultData
}