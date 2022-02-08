package com.xdevpro.trendingmoviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xdevpro.trendingmoviesapp.model.DetailMovieModel
import com.xdevpro.trendingmoviesapp.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    var movieDetail = MutableLiveData<DetailMovieModel>()
    val errorMessage = MutableLiveData<String>()

    fun getMovieDetail() {

        val response = repository.getMovieDetail()
        response.enqueue(object : Callback<DetailMovieModel> {
            override fun onResponse(call: Call<DetailMovieModel>, response: Response<DetailMovieModel>) {
                if (response.code() == 200) {
                    movieDetail.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<DetailMovieModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}