package com.xdevpro.trendingmoviesapp.model

data class ResponseModel (

    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<MovieModel>
)