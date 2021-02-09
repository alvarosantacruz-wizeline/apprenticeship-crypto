package com.github.alvarosct02.criptocurrency.data

data class UIState<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {

    companion object {
        fun <T> OnData(data: T?) = UIState<T>(data = data)
        fun <T> Error(message: String, data: T? = null) = UIState<T>(data = data, errorMessage = message)
        fun <T> Loading() = UIState<T>(isLoading = true)

        fun <T> fromResource(resource: Resource<T>): UIState<T> {
            return when (resource) {
                is Resource.Success -> OnData(resource.data)
                is Resource.Error -> Error(resource.errorType.message)
                is Resource.Loading -> Loading()
            }
        }
    }
}
