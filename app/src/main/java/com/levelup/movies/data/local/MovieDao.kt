package com.levelup.movies.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.levelup.movies.data.model.MovieItemResponse

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie : MovieItemResponse)

    @Query("SELECT * FROM MovieItemResponse")
    fun getMovies() : LiveData<List<MovieItemResponse>>

    @Delete
    fun deleteMovie(movie: MovieItemResponse)
}