package com.bms.showmymovie.data.api

import com.bms.showmymovie.data.api.models.movieCreditDetails.CastDetail
import com.bms.showmymovie.data.api.models.movieDetailsChanges.MovieDetailChanges
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movie:Int,
                                @Query("api_key") apiKey:String)
    : Response<MovieDetail>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movie:Int,
                                @Query("api_key") apiKey:String)
    : Response<CastDetail>

    @GET("movie/{movieId}/changes")
    suspend fun getMovieDetailsChanges(@Path("movieId") movie:Int,
                                @Query("api_key") apiKey:String)
    : Response<MovieDetailChanges>
}