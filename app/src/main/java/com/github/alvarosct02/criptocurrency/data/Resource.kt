package com.github.alvarosct02.criptocurrency.data

data class Resource<T>(val status: Int, val data: T?, val errorType: Int?) {
    fun isError() = status == STATUS_ERROR

    companion object {
        const val STATUS_LOADING = 0
        const val STATUS_SUCCESS = 1
        const val STATUS_ERROR = -1

        fun <T> success(data: T?): Resource<T> {
            return Resource(STATUS_SUCCESS, data, null)
        }

        fun <T> error(item: T? = null): Resource<T> {
            return Resource(STATUS_ERROR, item, null)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(STATUS_LOADING, data, null)
        }
    }
}