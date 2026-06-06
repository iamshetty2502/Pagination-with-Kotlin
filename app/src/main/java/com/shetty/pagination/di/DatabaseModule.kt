package com.shetty.pagination.di

import android.content.Context
import androidx.room.Room
import com.shetty.pagination.db.AppDatabase
import com.shetty.pagination.db.RestaurantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "restaurant_db"
        ).build()
    }

    @Provides
    fun provideRestaurantDao(database: AppDatabase): RestaurantDao {
        return database.restaurantDao()
    }
}
