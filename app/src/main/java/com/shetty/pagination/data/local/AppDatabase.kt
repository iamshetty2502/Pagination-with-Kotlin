package com.shetty.pagination.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shetty.pagination.data.local.dao.RemoteKeysDao
import com.shetty.pagination.data.local.dao.RestaurantDao
import com.shetty.pagination.data.local.entity.RemoteKeys
import com.shetty.pagination.data.local.entity.RestaurantEntity

@Database(
    entities = [RestaurantEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
