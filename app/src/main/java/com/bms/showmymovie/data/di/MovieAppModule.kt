package com.bms.showmymovie.data.di

import android.content.Context
import androidx.room.Room
import com.bms.showmymovie.data.api.MovieApi
import com.bms.showmymovie.common.Utils
import com.bms.showmymovie.data.db.MovieDao
import com.bms.showmymovie.data.db.MovieDatabase
import com.bms.showmymovie.data.repos.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieAppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun bindMovieApiService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideMovieDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context,
            MovieDatabase::class.java,
            MovieDatabase::class.simpleName!!)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCharacterDao(movieDatabase: MovieDatabase):MovieDao{
        return movieDatabase.movieDao()
    }
}