package com.levelup.movies

import android.app.Application
import com.levelup.movies.di.appModule
import com.levelup.movies.di.localModule
import com.levelup.movies.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Movies : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Movies)
            modules(listOf(remoteModule, localModule, appModule))
        }
    }
}