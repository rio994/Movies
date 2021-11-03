package com.levelup.movies.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.data.model.MovieListResponse
import com.levelup.movies.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository : Repository) : ViewModel() {
    val topRatedMovies = MutableLiveData<List<MovieItemResponse>>()
    val popularMovies = MutableLiveData<List<MovieItemResponse>>()
    val errorResponse = MutableLiveData<String>()

    fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getTopRatedMovies().enqueue(object : Callback<MovieListResponse>{
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if(response.body() != null && response.isSuccessful)
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
        repository.getPopularMovies().enqueue(object : Callback<MovieListResponse>{
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
}