package com.levelup.movies.data.remote

import com.levelup.movies.data.model.GenreResponse
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


    fun getSimilarMovie(id : Int) : Call<MovieListResponse>{
        return movieApi.getSimilarMovie(id)
    }

    fun getGenreList() : Call<List<GenreResponse>>{
        return movieApi.getGenreList()
    }

}