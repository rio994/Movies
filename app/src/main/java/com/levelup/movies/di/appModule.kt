package com.levelup.movies.di


import com.levelup.movies.data.repository.Repository
import com.levelup.movies.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository(get(), get()) }
    viewModel { MainViewModel(get()) }

}