package com.bms.showmymovie.data.api.models.movieDetailsChanges

data class Item(
    val action: String,
    val id: String,
    val iso_639_1: String,
    val original_value: String,
    val time: String,
    val value: String
)