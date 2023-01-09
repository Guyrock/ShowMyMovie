package com.bms.showmymovie.domain

import com.bms.showmymovie.common.ApiResult
import com.bms.showmymovie.data.api.models.movieDetailsChanges.MovieDetailChanges
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import com.bms.showmymovie.data.mappers.MovieDetailsMapper
import com.bms.showmymovie.data.repos.MovieRepository
import javax.inject.Inject

class MovieChangesUsecase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper
) {
    suspend operator fun invoke(movie:Int,currentMovieDetail: MovieDetail) : ApiResult<MovieDetail>{
        return when(val apiResult = movieRepository.getMovieDetailsChanges(movie)){
            is ApiResult.Success ->{
                checkForChanges(apiResult.data,currentMovieDetail)
            }
            is ApiResult.Error ->{
                ApiResult.Error(apiResult.string)
            }
        }
    }

    private fun checkForChanges(data: MovieDetailChanges, currentMovieDetail: MovieDetail): ApiResult<MovieDetail> {
        var changes = false
        //check for changes in movieDetails
        data.changes.forEach{ dataChange ->
            when(dataChange.key){
                "overview" ->{
                    if(!currentMovieDetail.overview.equals(dataChange.items[0].value)){
                        changes = true
                    }
                }
                "title" ->{
                    if(!currentMovieDetail.title.equals(dataChange.items[0].value)){
                        changes = true
                    }
                }
                else ->{
                }
            }
        }
        return if(changes){
            ApiResult.Success(currentMovieDetail)
        }else{
            ApiResult.Error("No changes")
        }
    }
}