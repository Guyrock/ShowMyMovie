package com.bms.showmymovie.data.repos

import com.bms.showmymovie.common.ApiResult
import com.bms.showmymovie.common.Utils
import com.bms.showmymovie.data.api.MovieApi
import com.bms.showmymovie.data.api.models.movieCreditDetails.CastDetail
import com.bms.showmymovie.data.api.models.movieDetailsChanges.MovieDetailChanges
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import com.bms.showmymovie.data.db.MovieDao
import com.bms.showmymovie.data.db.entities.CastBrief
import com.bms.showmymovie.data.db.entities.MovieBrief
import com.bms.showmymovie.data.mappers.MovieDetailsMapper
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val movieDetailsMapper: MovieDetailsMapper
) {

    suspend fun getMovieDetails(movie:Int) : ApiResult<MovieDetail> {
        return try {
            val response = movieApi.getMovieDetails(movie,Utils.API_KEY)
            if (response.isSuccessful) {
                if(response.body()!=null){
                    movieDao.insertMovieDetails(movieDetailsMapper.toMovieBrief(response.body()!!))
                    ApiResult.Success(response.body()!!)
                }else{
                    ApiResult.Error("No data found")
                }
            } else {
                ApiResult.Error("No data found")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.toString())
        }
    }

    fun getMovieDetailsFromLocalSource(movie:Int) : MovieBrief?{
        return movieDao.getMovieDetails(movie)
    }

    suspend fun getMovieCredits(movie:Int) : ApiResult<CastDetail> {
        return try {
            val response = movieApi.getMovieCredits(movie,Utils.API_KEY)
            if (response.isSuccessful) {
                if(response.body()!=null){
                    movieDao.insertMovieCredits(movieDetailsMapper.toCastBrief(response.body()!!))
                    ApiResult.Success(response.body()!!)
                }else{
                    ApiResult.Error("No data found")
                }
            } else {
                ApiResult.Error("No data found")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.toString())
        }
    }

    fun getMovieCreditsFromLocalSource(movie:Int) : CastBrief?{
        return movieDao.getMovieCredits(movie)
    }

    suspend fun getMovieDetailsChanges(movie:Int) : ApiResult<MovieDetailChanges> {
        return try {
            val response = movieApi.getMovieDetailsChanges(movie,Utils.API_KEY)
            if (response.isSuccessful) {
                if(response.body()!=null){
                    ApiResult.Success(response.body()!!)
                }else{
                    ApiResult.Error("No data found")
                }
            } else {
                ApiResult.Error("No data found")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.toString())
        }
    }
}