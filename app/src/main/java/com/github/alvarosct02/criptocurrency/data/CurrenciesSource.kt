package com.github.alvarosct02.criptocurrency.data

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker

interface CurrenciesSource {

    suspend fun getAllBooks(): List<Book>

    suspend fun saveAllBooks(books: List<Book>)

    suspend fun getTickerByBook(book: String): Ticker

    suspend fun saveBookTicker(ticker: Ticker)

    suspend fun getOrdersByBook(book: String): BookOrders

    suspend fun saveBookOrders(bookOrders: BookOrders)

}