package com.github.alvarosct02.criptocurrency.features.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.Event
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
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currencyList = MutableLiveData<List<Book>>()
    val items: LiveData<List<Book>> = _currencyList

    private val _openBookEvent = MutableLiveData<Event<Book>>()
    val openBookEvent: LiveData<Event<Book>> = _openBookEvent


    //    TODO: Pending Refactor with ServiceLocator/DI
    private val currencyService = RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
        .create<CurrenciesService>()


    fun getAvailableBooks() {

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

    fun onBookSelected(book: Book) {
        _openBookEvent.value = Event(book)
    }
}