package com.israelmekomou.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.israelmekomou.data.local.dao.MovieDao
import com.israelmekomou.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CineScopeDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}