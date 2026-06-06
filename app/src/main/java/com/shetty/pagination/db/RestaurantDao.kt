package com.shetty.pagination.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<RestaurantEntity>)

    @Query("SELECT * FROM restaurants WHERE radius = :radius ORDER BY page ASC")
    fun getRestaurantsByRadius(radius: Int): PagingSource<Int, RestaurantEntity>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getRestaurantById(id: String): RestaurantEntity?

    @Query("DELETE FROM restaurants WHERE radius = :radius")
    suspend fun clearAllByRadius(radius: Int)
}
