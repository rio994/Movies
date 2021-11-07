package com.levelup.movies.ui.home

import com.levelup.movies.util.MovieCategory

interface MovieCategoryAdapterListener {

    fun onMovieCategoryItemClick(id : Int, movieCategory: MovieCategory)
}