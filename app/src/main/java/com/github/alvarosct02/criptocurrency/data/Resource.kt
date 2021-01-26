package com.github.alvarosct02.criptocurrency.data

sealed class Resource<out T>(val data: T?) {

    class Success<T>(data: T?) : Resource<T>(data)
    class Error<out T>(val message: String, data: T?) : Resource<T>(data)
    class Loading<out T>(data: T?) : Resource<T>(data)

}
