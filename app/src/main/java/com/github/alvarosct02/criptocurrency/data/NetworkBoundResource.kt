package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

abstract class NetworkBoundResource<ResultType, RequestType>() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    fun get() {
        result.value = Resource.Loading(null)
        val dbSource = loadFromDb()

        result.addSource(dbSource, object : Observer<ResultType> {
            var isFirst = true
            override fun onChanged(data: ResultType?) {
                if (isFirst && shouldFetch(data)) {
                    setValue(Resource.Loading(data))
                    isFirst = false
                    fetchFromNetwork(dbSource)
                } else {
                    setValue(Resource.Success(data))
                    isFirst = false
                }
            }
        })

    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            response?.let {
                saveCallResult(response)
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected abstract fun saveCallResult(item: RequestType)

    protected fun shouldFetch(data: ResultType?): Boolean = true

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun createCall(): LiveData<RequestType>
}