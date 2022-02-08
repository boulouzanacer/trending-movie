package com.xdevpro.trendingmoviesapp.model


data class MovieModel(
    val adult: Boolean,
    val id : Int,
    val original_language: String,
    val overview : String,
    val release_date:String,
    val title: String,
    val poster_path: String,
    val vote_average: Double)