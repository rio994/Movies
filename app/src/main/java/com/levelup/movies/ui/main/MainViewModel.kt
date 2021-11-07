package com.levelup.movies.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.movies.data.model.GenreItem
import com.levelup.movies.data.model.GenreListResponse
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.data.model.MovieListResponse
import com.levelup.movies.data.repository.Repository
import com.levelup.movies.util.MovieCategory
import com.levelup.movies.util.combineStrings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val topRatedMovies = MutableLiveData<List<MovieItemResponse>>()
    val popularMovies = MutableLiveData<List<MovieItemResponse>>()
    val similarMovies = MutableLiveData<List<MovieItemResponse>>()
    val favoriteMovies = repository.getFavoriteMovies()
    val errorResponse = MutableLiveData<String>()
    var genreList = listOf<GenreItem>()
    val singleMovie = MutableLiveData<MovieItemResponse>()

    init {
        getGenreList()
    }

    fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getTopRatedMovies().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.body() != null && response.isSuccessful)
                    topRatedMovies.postValue(response.body()?.results)
                else
                    errorResponse.postValue(response.errorBody().toString())
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                errorResponse.postValue(t.localizedMessage)
            }
        })
    }

    fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getPopularMovies().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.body() != null && response.isSuccessful)
                    popularMovies.postValue(response.body()?.results)
                else
                    errorResponse.postValue(response.errorBody().toString())
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                errorResponse.postValue(t.localizedMessage)
            }
        })
    }

    private fun getGenreList() = viewModelScope.launch(Dispatchers.IO) {
        repository.getGenreList().enqueue(object : Callback<GenreListResponse> {
            override fun onResponse(
                call: Call<GenreListResponse>,
                response: Response<GenreListResponse>
            ) {
                if (response.isSuccessful)
                    response.body()?.let {
                        genreList = it.genres
                    }
                else
                    errorResponse.postValue(response.errorBody().toString())
            }

            override fun onFailure(call: Call<GenreListResponse>, t: Throwable) {
                errorResponse.postValue(t.localizedMessage)
            }

        })
    }

    fun getSimilarMovies(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getSimilarMovies(id).enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.body() != null && response.isSuccessful)
                    similarMovies.postValue(response.body()?.results)
                else
                    errorResponse.postValue(response.errorBody().toString())
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                errorResponse.postValue(t.localizedMessage)
            }
        })
    }

    fun getMovieById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getMovieById(id).enqueue(object : Callback<MovieItemResponse> {
            override fun onResponse(
                call: Call<MovieItemResponse>,
                response: Response<MovieItemResponse>
            ) {
                if (response.body() != null && response.isSuccessful)
                    singleMovie.postValue(response.body())
                else
                    errorResponse.postValue(response.errorBody().toString())
            }

            override fun onFailure(call: Call<MovieItemResponse>, t: Throwable) {
                errorResponse.postValue(t.localizedMessage)
            }
        })
    }

    fun getMovieFromCategoryById(id: Int, movieCategory: MovieCategory) = when (movieCategory) {
        MovieCategory.TOP_RATED -> topRatedMovies.value?.first { it.id == id }
        MovieCategory.POPULAR -> popularMovies.value?.first { it.id == id }
        MovieCategory.SIMILAR -> similarMovies.value?.firstOrNull { it.id == id }
            .also { if (it == null) getMovieById(id) }
        MovieCategory.FAVORITES -> favoriteMovies.value?.firstOrNull { it.id == id }
            .also { if (it == null) getMovieById(id) }
    }


    fun getGenresByIds(movie: MovieItemResponse) =
        movie.genres?.let {
            it.map { it.name }
                .combineStrings()
        } ?: movie.genreIdList?.let {
            genreList
                .filter { genre -> it.contains(genre.id) }
                .map { it.name }
                .combineStrings()
        } ?: ""

    fun setFavorite(movie: MovieItemResponse, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite)
                repository.addToFavorite(movie)
            else
                repository.removeFromFavorites(movie)
        }
    }

    fun checkIsFavorite(movie: MovieItemResponse): Boolean {
        return favoriteMovies.value?.map { it.id }?.contains(movie.id) ?: false
    }


}