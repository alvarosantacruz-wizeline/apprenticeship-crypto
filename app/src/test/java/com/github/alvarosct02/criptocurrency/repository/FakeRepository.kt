package com.github.alvarosct02.criptocurrency.repository

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FakeRepository: CurrenciesRepository {
    override fun observeAllBooks(): LiveData<List<Book>> {
        TODO("Not yet implemented")
    }

    override fun observeTickerByBook(book: String): LiveData<Ticker> {
        TODO("Not yet implemented")
    }

    override fun observeOrdersByBook(book: String): LiveData<BookOrders> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBooks(): Resource<List<Book>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTickerByBook(book: String): Resource<Ticker> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByBook(book: String): Resource<BookOrders> {
        TODO("Not yet implemented")
    }
}