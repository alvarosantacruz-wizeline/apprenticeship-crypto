package com.github.alvarosct02.criptocurrency.features.currencyDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CurrencyDetailViewModel : ViewModel() {

//    TODO: Split in different loaders
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _ticker = MutableLiveData<Ticker>()
    val ticker: LiveData<Ticker> = _ticker

    private val _orders = MutableLiveData<BookOrders>()
    val orders: LiveData<BookOrders> = _orders


    //    TODO: Pending Refactor with ServiceLocator/DI
    private val currencyService = RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
        .create<CurrenciesService>()


    fun getBookTicker(book: String) {

        _isLoading.value = true

        //    TODO: Pending Refactor with Coroutines
        currencyService.getBookDetail(book).enqueue(object : Callback<BitsoWrapper<Ticker>> {
            override fun onResponse(
                call: Call<BitsoWrapper<Ticker>>,
                response: Response<BitsoWrapper<Ticker>>
            ) {
                _ticker.value = response.body()?.payload
                _isLoading.value = false
            }

            override fun onFailure(call: Call<BitsoWrapper<Ticker>>, t: Throwable) {
                t.printStackTrace()
                _isLoading.value = false
            }

        })
    }

    fun getBookOrders(book: String) {

        _isLoading.value = true

        //    TODO: Pending Refactor with Coroutines
        currencyService.getBookOrders(book).enqueue(object : Callback<BitsoWrapper<BookOrders>> {
            override fun onResponse(
                call: Call<BitsoWrapper<BookOrders>>,
                response: Response<BitsoWrapper<BookOrders>>
            ) {
                _orders.value = response.body()?.payload
                _isLoading.value = false
            }

            override fun onFailure(call: Call<BitsoWrapper<BookOrders>>, t: Throwable) {
                t.printStackTrace()
                _isLoading.value = false
            }

        })
    }
}