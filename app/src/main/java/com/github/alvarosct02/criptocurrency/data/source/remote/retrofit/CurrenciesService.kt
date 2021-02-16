package com.github.alvarosct02.criptocurrency.data.source.remote.retrofit

import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.Trade
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesService {

    @GET("ticker")
    suspend fun listAvailableBooks(): BitsoWrapper<List<Ticker>>

    @GET("ticker")
    suspend fun getBookDetail(
        @Query("book") book: String
    ): BitsoWrapper<Ticker>

    @GET("ohlc")
    suspend fun getTickerHistory(
        @Query("book") book: String,
        @Query("time_bucket") timeBucket: Long,
        @Query("start") start: Long,
        @Query("end") end: Long,
    ): BitsoWrapper<List<TickerHistory>>

    @GET("order_book")
    suspend fun getBookOrders(
        @Query("book") book: String
    ): BitsoWrapper<BookOrders>

    @GET("trades")
    suspend fun getTrades(
        @Query("book") book: String
    ): BitsoWrapper<List<Trade>>
}
