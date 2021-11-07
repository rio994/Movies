package com.levelup.movies.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieItemResponse(
    @PrimaryKey()
    val id : Int,
    @SerializedName("poster_path")
    val posterPath : String,
    @SerializedName("backdrop_path")
    val backdropPath : String,
    val title : String,
    @SerializedName("release_date")
    val releaseDate : String,
    @SerializedName("genre_ids")
    val genreIdList : List<Int>?,
    val overview : String,
    @SerializedName("vote_average")
    val rating : Float
){
    @Ignore
    var genres : List<GenreItem>? = null

    fun getBackdropUrl() : String{
        return "https://image.tmdb.org/t/p/w400$backdropPath"
    }

    fun getPosterUrl() : String{
        return "https://image.tmdb.org/t/p/w400$posterPath"
    }

}
