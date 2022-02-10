package com.xdevpro.trendingmoviesapp.repository

import RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService, private val movie_id : String) {

    private var api_key = "c9856d0cb57c3f14bf75bdc6c063b8f3"

    fun getAllMovies() = retrofitService.getAllMovies(api_key)
    fun getMovieDetail() = retrofitService.getMovieDetail(movie_id, api_key)
}