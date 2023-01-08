package com.bms.showmymovie.domain

import com.bms.showmymovie.common.ApiResult
import com.bms.showmymovie.data.api.models.movieCreditDetails.CastDetail
import com.bms.showmymovie.data.mappers.MovieDetailsMapper
import com.bms.showmymovie.data.repos.MovieRepository
import javax.inject.Inject

class MovieCreditsUsecase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper
){
    suspend operator fun invoke(movie:Int) : ApiResult<CastDetail> {
        val localResult = movieRepository.getMovieCreditsFromLocalSource(movie)
        localResult?.let {
            return ApiResult.Success(movieDetailsMapper.toCastDetail(it))
        } ?:
        return movieRepository.getMovieCredits(movie)
    }
}