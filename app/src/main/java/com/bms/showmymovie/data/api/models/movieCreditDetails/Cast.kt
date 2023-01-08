package com.bms.showmymovie.data.api.models.movieCreditDetails

data class Cast(
    val character: String,
    val name: String,
    val order: Int,
    val profile_path: String?
)