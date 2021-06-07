package com.example.whatcomapp.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CacheEntity::class], version = 1, exportSchema = false)

abstract class CacheDatabase : RoomDatabase() {

    abstract val CacheDatabaseDao: CacheDatabaseDao
    companion object {

        @Volatile
        private var INSTANCE: CacheDatabase? = null
        fun getInstance(context: Context): CacheDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CacheDatabase::class.java,
                        "cache_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return instance

            }

        }

    }

}