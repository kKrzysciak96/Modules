package com.example.core.utils

//sealed class ApiResult<out T> {
//    data class Success<out T>(val data: T) : ApiResult<T>()
//    data class Error(val message: String?) : ApiResult<Unit>()
//    object Loading : ApiResult<Unit>()
//}


sealed interface ApiResult {
    data class Success<R>(val data: R) : ApiResult
    data class Error(val message: String?) : ApiResult
    object Loading : ApiResult
}
