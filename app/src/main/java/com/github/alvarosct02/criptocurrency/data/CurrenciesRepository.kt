package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import org.jetbrains.annotations.TestOnly

interface CurrenciesRepository {

    fun observeAllBooks(): LiveData<List<Book>>

    fun observeTickerByBook(book: String): LiveData<Ticker>

    fun observeOrdersByBook(book: String): LiveData<BookOrders>

    suspend fun getAllBooks(): Resource<List<Book>>

    suspend fun getTickerByBook(book: String): Resource<Ticker>

    suspend fun getOrdersByBook(book: String): Resource<BookOrders>

}
