package com.example.clickforcesdktest.data.model


//TODO: 是否要繼續使用這方法  還是另外做一個error response data class 就好
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val throwable: Throwable) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}