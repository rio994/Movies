package com.levelup.movies.data.remote

import com.levelup.movies.data.model.GenreListResponse
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.data.model.MovieListResponse
import retrofit2.Call

class MovieService (private val movieApi: MovieApi) {

    fun getTopRatedMovies() : Call<MovieListResponse>{
        return movieApi.getTopRatedMovies()
    }

    fun getPopularMovies() : Call<MovieListResponse>{
        return movieApi.getPopularMovies()
    }


    fun getMovieById(id : Int) : Call<MovieItemResponse>{
        return movieApi.getMovieById(id)
    }


    fun getSimilarMovies(id : Int) : Call<MovieListResponse> = movieApi.getSimilarMovies(id)


    fun getGenreList() : Call<GenreListResponse> = movieApi.getGenreList()


}