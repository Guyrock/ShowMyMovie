package com.bms.showmymovie.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bms.showmymovie.data.api.models.movieCreditDetails.Cast

@Entity(tableName = "tbl_movie_credits")
data class CastBrief (
    val cast: List<Cast>,
    @PrimaryKey
    val id: Int
)