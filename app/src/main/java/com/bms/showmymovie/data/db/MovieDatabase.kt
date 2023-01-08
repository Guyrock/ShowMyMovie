package com.bms.showmymovie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bms.showmymovie.data.db.entities.CastBrief
import com.bms.showmymovie.data.db.entities.MovieBrief
import com.bms.showmymovie.data.db.typeconverters.MovieTypeConverters

@Database(entities = [MovieBrief::class,CastBrief::class], version = 5)
@TypeConverters(MovieTypeConverters::class)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
}