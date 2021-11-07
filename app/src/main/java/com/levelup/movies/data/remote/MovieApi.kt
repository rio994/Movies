package com.levelup.movies.data.remote

import com.levelup.movies.data.model.GenreItem
import com.levelup.movies.data.model.GenreListResponse
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.data.model.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies() : Call<MovieListResponse>

    @GET("movie/popular")
    fun getPopularMovies() : Call<MovieListResponse>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id : Int) : Call<MovieItemResponse>

    @GET("movie/{id}/similar")
    fun getSimilarMovies(@Path("id") id : Int) : Call<MovieListResponse>

    @GET("genre/movie/list")
    fun getGenreList() : Call<GenreListResponse>



}