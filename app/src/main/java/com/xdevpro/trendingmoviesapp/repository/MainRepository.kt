package com.xdevpro.trendingmoviesapp.repository

import RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService, private val movie_id : String) {

    fun getAllMovies() = retrofitService.getAllMovies()
    fun getMovieDetail() = retrofitService.getMovieDetail(movie_id)
}