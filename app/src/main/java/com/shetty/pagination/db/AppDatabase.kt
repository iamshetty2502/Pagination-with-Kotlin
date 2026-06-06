package com.shetty.pagination.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RestaurantEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
