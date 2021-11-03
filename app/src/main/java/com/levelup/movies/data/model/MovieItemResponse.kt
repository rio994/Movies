package com.levelup.movies.data.model

data class MovieItemResponse(
    val poster_path : String,
    val backdrop_path : String,
    val title : String,
    val release_date : String,
    val genre_ids : List<Int>,
    val overview : String
){
    fun getImageUrl() : String{
        return "https://image.tmdb.org/t/p/w400$poster_path"
    }
}
