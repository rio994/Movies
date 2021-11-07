package com.levelup.movies.data.model

import androidx.room.Entity

@Entity
data class GenreItem(
    val id : Int,
    val name : String
)
