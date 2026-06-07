package com.shetty.pagination.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shetty.pagination.data.local.entity.RestaurantEntity

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<RestaurantEntity>)

    @Query("SELECT * FROM restaurants WHERE radius = :radius ORDER BY page ASC")
    fun getRestaurantsByRadius(radius: Int): PagingSource<Int, RestaurantEntity>

    @Query("DELETE FROM restaurants WHERE radius = :radius")
    suspend fun clearAllByRadius(radius: Int)
}
