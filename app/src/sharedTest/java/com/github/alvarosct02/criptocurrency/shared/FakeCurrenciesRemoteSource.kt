package com.github.alvarosct02.criptocurrency.shared

import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FakeCurrenciesRemoteSource : CurrenciesRemoteSource {

    private lateinit var service: CurrenciesService
    private lateinit var mockWebServer: MockWebServer

    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrenciesService::class.java)
    }

    fun stopService() {
        mockWebServer.shutdown()
    }

    override suspend fun getAllBooks(): List<Ticker> {
        enqueueResponse("all_books.json")
        val result = runBlocking { service.listAvailableBooks() }
        return result.payload
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        enqueueResponse("ticker_btc_mxn.json")
        val result = runBlocking { service.getBookDetail("btc_mxn") }
        return result.payload
    }

    override suspend fun getTickerHistoryByBook(book: String): List<TickerHistory> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        enqueueResponse("orders_btc_mxn.json")
        val result = runBlocking { service.getBookOrders("btc_mxn") }
        return result.payload
    }

    override suspend fun getTrades(book: String): List<Trade> {
        TODO("Not yet implemented")
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}
