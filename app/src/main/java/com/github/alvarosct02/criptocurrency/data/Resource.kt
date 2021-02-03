package com.github.alvarosct02.criptocurrency.data

sealed class Resource<T>(val data: T?) {

    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(val errorType: ErrorType, data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)

}

sealed class ErrorType(val message: String) {

    object TimeOut : ErrorType("TimeOut")
    class ServerError(message: String) : ErrorType(message)
    object Unknown : ErrorType("Unknown")

}
