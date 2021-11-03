package com.levelup.movies.data.repository

import com.levelup.movies.data.remote.MovieService

class Repository (private val movieService: MovieService) {

    fun getTopRatedMovies() = movieService.getTopRatedMovies()


    fun getPopularMovies() = movieService.getPopularMovies()


    fun getMovieById(id : Int) = movieService.getMovieById(id)


    fun getSimilarMovie(id: Int) =  movieService.getSimilarMovie(id)


    fun getGenreList() = movieService.getGenreList()

}