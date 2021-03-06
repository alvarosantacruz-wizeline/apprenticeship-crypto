package com.github.alvarosct02.criptocurrency.ui.currencyDetail

import androidx.lifecycle.*
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker

class CurrencyDetailViewModel(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    private val _bookId = MutableLiveData<String>()

    val ticker: LiveData<UIState<Ticker>> = _bookId.switchMap { book ->
        if (book.isBlank()) liveData { emit(UIState.Loading<Ticker>()) }
        else Transformations.map(currenciesRepository.getTickerByBook(book)) {
            UIState.fromResource(it)
        }
    }

    val orders: LiveData<UIState<BookOrders>> = _bookId.switchMap { book ->
        if (book.isBlank()) liveData { emit(UIState.Loading<BookOrders>()) }
        else Transformations.map(currenciesRepository.getOrdersByBook(book)) {
            UIState.fromResource(it)
        }
    }

    fun setBook(book: String) {
        _bookId.postValue(book)
    }
}
