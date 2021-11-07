package com.levelup.movies.di

import android.app.Application
import androidx.room.Room
import com.levelup.movies.data.local.AppDatabase
import org.koin.dsl.module

val localModule = module {
    fun provideDatabase(application : Application) : AppDatabase{
        val builder = Room.databaseBuilder(application, AppDatabase::class.java, "MoviesDB").fallbackToDestructiveMigration()
        return builder.build()
    }
    single { provideDatabase(get()) }
}