package com.daya.core.data

sealed class Resource<out T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val exceptionMessage: String?): Resource<Nothing>()
    object Loading : Resource<Nothing>()

    companion object
}
