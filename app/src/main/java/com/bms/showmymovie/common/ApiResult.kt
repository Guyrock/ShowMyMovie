package com.bms.showmymovie.common

sealed class ApiResult<out R>{
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val string: String) : ApiResult<Nothing>()
}
