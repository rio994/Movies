package com.levelup.movies.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.levelup.movies.data.remote.HeaderInterceptor
import com.levelup.movies.data.remote.MovieApi
import com.levelup.movies.data.remote.MovieService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {

    val url = "https://api.themoviedb.org/3/"

    fun provideGson() : Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()

    fun provideHttpClient() : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(HeaderInterceptor())
        .build()

    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(),get()) }
    single { get<Retrofit>().create(MovieApi::class.java) }
    single { MovieService(get()) }

}