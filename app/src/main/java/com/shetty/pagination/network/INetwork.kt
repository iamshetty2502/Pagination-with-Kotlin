package com.shetty.pagination.network

import com.shetty.pagination.models.Businesses
import com.shetty.pagination.models.ResultData
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("/v3/businesses/{id}")
    suspend fun getBusinessDetails(
        @Path("id") id: String
    ): Businesses
}
