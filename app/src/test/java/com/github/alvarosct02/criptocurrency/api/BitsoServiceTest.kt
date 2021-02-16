package com.github.alvarosct02.criptocurrency.api

import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class BitsoServiceTest {

    private lateinit var service: CurrenciesService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrenciesService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getAllTickers() {
        enqueueResponse("all_tickers.json")
        val bookList = runBlocking {
            service.listAvailableBooks()
        }

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/ticker"))

        assertThat(bookList.success, `is`(true))
        assertThat(bookList.payload, notNullValue())
        assertThat(bookList.payload.size, `is`(23))
    }

    @Test
    fun getBookDetail() {
        enqueueResponse("ticker_btc_mxn.json")
        val bookList = runBlocking {
            service.getBookDetail("btc_mxn")
        }

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/ticker?book=btc_mxn"))

        assertThat(bookList.success, `is`(true))
        assertThat(bookList.payload, notNullValue())
        assertThat(bookList.payload.book, `is`("btc_mxn"))
        assertThat(bookList.payload.volume, `is`("147.17803599"))
        assertThat(bookList.payload.low, `is`("619500.01"))
        assertThat(bookList.payload.vwap, `is`("647368.7986248928"))
    }

    @Test
    fun getBookOrders() {
        enqueueResponse("orders_btc_mxn.json")
        val bookList = runBlocking {
            service.getBookOrders("btc_mxn")
        }

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/order_book?book=btc_mxn"))

        assertThat(bookList.success, `is`(true))
        assertThat(bookList.payload, notNullValue())
        assertThat(bookList.payload.updatedAt, `is`("2021-01-25T06:11:07+00:00"))
        assertThat(bookList.payload.sequence, `is`("857783041"))
        assertThat(bookList.payload.bids.isEmpty(), `is`(false))
        assertThat(bookList.payload.asks.isEmpty(), `is`(false))
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
