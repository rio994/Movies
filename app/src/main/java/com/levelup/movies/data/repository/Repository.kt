package com.levelup.movies.data.repository

import com.levelup.movies.data.local.AppDatabase
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.data.remote.MovieService

class Repository (private val movieService: MovieService, private val database: AppDatabase) {

    fun getTopRatedMovies() = movieService.getTopRatedMovies()

    fun getPopularMovies() = movieService.getPopularMovies()

    fun getMovieById(id : Int) = movieService.getMovieById(id)

    fun getSimilarMovies(id: Int) =  movieService.getSimilarMovies(id)

    fun getGenreList() = movieService.getGenreList()

    fun addToFavorite(movie : MovieItemResponse) = database.movieDao().insertMovie(movie)

    fun getFavoriteMovies() = database.movieDao().getMovies()

    fun removeFromFavorites(movie: MovieItemResponse) = database.movieDao().deleteMovie(movie)

}