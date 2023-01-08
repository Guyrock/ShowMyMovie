package com.bms.showmymovie.data.mappers

import com.bms.showmymovie.data.api.models.movieCreditDetails.CastDetail
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import com.bms.showmymovie.data.db.entities.CastBrief
import com.bms.showmymovie.data.db.entities.MovieBrief
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(){

    fun toMovieDetail(movieBrief: MovieBrief) : MovieDetail {
        return MovieDetail(movieBrief.backdrop_path,movieBrief.genres,movieBrief.id,movieBrief.original_language,movieBrief.original_title,
            movieBrief.overview,movieBrief.poster_path,movieBrief.release_date,movieBrief.runtime,movieBrief.spoken_languages,
            movieBrief.tagline,movieBrief.title,movieBrief.vote_average,movieBrief.vote_count,true)
    }

    fun toMovieBrief(movieDetail: MovieDetail) : MovieBrief{
        return MovieBrief(movieDetail.id,movieDetail.backdrop_path,movieDetail.genres,movieDetail.original_language,movieDetail.original_title,
            movieDetail.overview,movieDetail.poster_path,movieDetail.release_date,movieDetail.runtime,movieDetail.spoken_languages,
            movieDetail.tagline,movieDetail.title,movieDetail.vote_average,movieDetail.vote_count)
    }

    fun toCastDetail(castBrief: CastBrief) : CastDetail {
        return CastDetail(castBrief.cast,castBrief.id)
    }

    fun toCastBrief(castDetail: CastDetail) : CastBrief{
        return CastBrief(castDetail.cast,castDetail.id)
    }
}