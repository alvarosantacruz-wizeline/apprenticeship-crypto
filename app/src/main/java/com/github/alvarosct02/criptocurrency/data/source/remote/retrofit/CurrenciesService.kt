package com.github.alvarosct02.criptocurrency.data.source.remote.retrofit

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesService {

    @GET("available_books")
    suspend fun listAvailableBooks(): BitsoWrapper<List<Book>>

    @GET("ticker")
    suspend fun getBookDetail(
        @Query("book") book: String
    ): BitsoWrapper<Ticker>

    @GET("order_book")
    suspend fun getBookOrders(
        @Query("book") book: String
    ): BitsoWrapper<BookOrders>

}