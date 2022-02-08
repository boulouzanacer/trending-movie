package com.xdevpro.trendingmoviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xdevpro.trendingmoviesapp.model.MovieModel
import com.xdevpro.trendingmoviesapp.model.ResponseModel
import com.xdevpro.trendingmoviesapp.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    var movieList = MutableLiveData<List<MovieModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {

        val response = repository.getAllMovies()
        response.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.code() == 200) {
                    movieList.postValue(response.body()!!.results)
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}