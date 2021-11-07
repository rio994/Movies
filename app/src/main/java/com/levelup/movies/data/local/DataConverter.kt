package com.levelup.movies.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.levelup.movies.data.model.MovieItemResponse
import java.lang.reflect.Type


class DataConverter {

    @TypeConverter
    fun fromGenreIdsToString(genreIds: List<Int?>?): String? {
        if (genreIds == null)
            return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.toJson(genreIds, type)
    }

    @TypeConverter
    fun fromStringToGenreIds(genres: String?): List<Int>? {
        if (genres == null)
            return null

        val gson = Gson()
        val type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson<List<Int>>(genres, type)
    }
}