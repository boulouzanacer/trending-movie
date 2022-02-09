package com.xdevpro.trendingmoviesapp.model


data class DetailMovieModel(
    val adult: Boolean,
    val backdrop_path: String,
    val budget : Double,
    val homepage: String,
    val original_language : String,
    val original_title:String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Double,
    val status: String,
    val vote_average: Double,
    val vote_count: Double,
    val production_companies: List<Production_companyModel>,
    val genres: List<GenresModel>,
    val production_countries: List<Production_countriesModel>,
    val spoken_languages: List<Spoken_languagesModel>
)