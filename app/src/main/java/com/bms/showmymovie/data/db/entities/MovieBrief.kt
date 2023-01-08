package com.bms.showmymovie.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.Genre
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.SpokenLanguage

@Entity(tableName = "tbl_movie_details")
data class MovieBrief(
    @PrimaryKey
    val id : Int,
    val backdrop_path: String?,
    val genres: List<Genre>?,
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
    val vote_count: Int?
)