package com.github.alvarosct02.criptocurrency.data.source.remote.retrofit

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesService {

    @GET("available_books")
    fun listAvailableBooks(): Call<BitsoWrapper<List<Book>>>

    @GET("ticker")
    fun getBookDetail(
        @Query("book") book: String
    ): Call<BitsoWrapper<Ticker>>

}