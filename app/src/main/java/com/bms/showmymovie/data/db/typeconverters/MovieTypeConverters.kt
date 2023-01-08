package com.bms.showmymovie.data.db.typeconverters

import androidx.room.TypeConverter
import com.bms.showmymovie.data.api.models.movieCreditDetails.Cast
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.Genre
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.SpokenLanguage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class MovieTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringTogenreList(data: String?): List<Genre?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson<List<Genre?>>(data, listType)
    }

    @TypeConverter
    fun genreListToString(someObjects: List<Genre?>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringTolangList(data: String?): List<SpokenLanguage?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<SpokenLanguage?>?>() {}.type
        return gson.fromJson<List<SpokenLanguage?>>(data, listType)
    }

    @TypeConverter
    fun langListToString(someObjects: List<SpokenLanguage?>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringTocastList(data: String?): List<Cast?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Cast?>?>() {}.type
        return gson.fromJson<List<Cast?>>(data, listType)
    }

    @TypeConverter
    fun castListToString(someObjects: List<Cast?>?): String? {
        return gson.toJson(someObjects)
    }
}