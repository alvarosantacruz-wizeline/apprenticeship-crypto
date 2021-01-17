package com.github.alvarosct02.criptocurrency.features.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CurrencyListViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    private val _currencyList = MutableLiveData<List<Book>>()

    val items: LiveData<List<Book>> = _currencyList
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAvailableBooks() {

        //    TODO: Pending Refactor with ServiceLocator
        val currencyService = RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
            .create<CurrenciesService>()

        _isLoading.value = true

        //    TODO: Pending Refactor with Coroutines
        currencyService.listAvailableBooks().enqueue(object : Callback<BitsoWrapper<List<Book>>> {
            override fun onResponse(
                call: Call<BitsoWrapper<List<Book>>>,
                response: Response<BitsoWrapper<List<Book>>>
            ) {
                _currencyList.value = response.body()?.payload
                _isLoading.value = false
            }

            override fun onFailure(call: Call<BitsoWrapper<List<Book>>>, t: Throwable) {
                t.printStackTrace()
                _isLoading.value = false
            }

        })
    }
}