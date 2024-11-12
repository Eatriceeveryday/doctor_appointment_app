package com.mufr.klinikku.domain.entities.general

sealed class Resource<T>(val data: T? , val message: String?) {
    class Success<T>(data: T?) : Resource<T>(data, null )
    class Error<T>(message: String?): Resource<T>(null, message)
    class AuthError<T>(message: String?): Resource<T>(null, message)
}

