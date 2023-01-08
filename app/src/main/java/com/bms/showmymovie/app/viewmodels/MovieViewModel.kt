package com.bms.showmymovie.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.showmymovie.common.ApiResult
import com.bms.showmymovie.data.api.models.movieCreditDetails.CastDetail
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import com.bms.showmymovie.domain.GetFavMovieUsecase
import com.bms.showmymovie.domain.MovieChangesUsecase
import com.bms.showmymovie.domain.MovieCreditsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getFavMovieUsecase: GetFavMovieUsecase,
    private val movieCreditsUsecase: MovieCreditsUsecase,
    private val changesUsecase: MovieChangesUsecase
) : ViewModel() {

    private val _details = MutableLiveData<MovieDetail>()
    val details : LiveData<MovieDetail> = _details

    private val _credits = MutableLiveData<CastDetail>()
    val credits : LiveData<CastDetail> = _credits

    private val _error = MutableLiveData<String>()
    val error = _error

    private val _creditserror = MutableLiveData<String>()
    val creditserror = _error

    fun getFavMovieDetails(movie:Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getFavMovieUsecase.invoke(movie)){
                is ApiResult.Success -> {
                    val currentMovieDetail = result.data
                    _details.postValue(currentMovieDetail)
                    if(currentMovieDetail.fromRoom){
                        getMovieDetailsChanges(movie,currentMovieDetail)
                    }
                }
                is ApiResult.Error -> {
                    _error.postValue(result.string)
                }
            }
        }
    }

    private fun getMovieDetailsChanges(movie: Int,currentMovieDetail: MovieDetail) {
        viewModelScope.launch (Dispatchers.IO){
            val result = changesUsecase.invoke(movie,currentMovieDetail)
        }
    }

    fun getMovieCredits(movie:Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = movieCreditsUsecase.invoke(movie)){
                is ApiResult.Success -> {
                    _credits.postValue(result.data!!)
                }
                is ApiResult.Error -> {
                    _creditserror.postValue(result.string)
                }
            }
        }
    }
}