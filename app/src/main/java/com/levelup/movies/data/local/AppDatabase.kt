package com.levelup.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.levelup.movies.data.model.MovieItemResponse

@Database(entities = [MovieItemResponse::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}