package com.bms.showmymovie.domain

import com.bms.showmymovie.common.ApiResult
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import com.bms.showmymovie.data.mappers.MovieDetailsMapper
import com.bms.showmymovie.data.repos.MovieRepository
import javax.inject.Inject

class GetFavMovieUsecase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper
) {
    suspend operator fun invoke(movie:Int) : ApiResult<MovieDetail>{
        val localResult = movieRepository.getMovieDetailsFromLocalSource(movie)
        localResult?.let {
            return ApiResult.Success(movieDetailsMapper.toMovieDetail(it))
        } ?:
        return movieRepository.getMovieDetails(movie)
    }
}