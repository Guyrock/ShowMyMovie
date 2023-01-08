package com.bms.showmymovie.data.api.models.moviePrimaryDetails

import java.io.Serializable

data class MovieDetail(
    val backdrop_path: String?,
    val genres: List<Genre>?,
    val id: Int,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val tagline: String?,
    val title: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val fromRoom: Boolean = false
) : Serializable