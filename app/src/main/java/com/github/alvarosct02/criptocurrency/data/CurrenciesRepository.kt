package com.github.alvarosct02.criptocurrency.data

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.CurrenciesSource
import java.lang.Exception

class CurrenciesRepository(
    private val remote: CurrenciesSource,
    private val local: CurrenciesSource,
): CurrenciesSource {

    override suspend fun getAllBooks(): List<Book> {
        return try {
            remote.getAllBooks().also {
                local.saveAllBooks(it)
            }
        } catch (e:Exception) {
            local.getAllBooks()
        }
    }

    override suspend fun saveAllBooks(books: List<Book>) {
        TODO("Not yet implemented")
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        return try {
            remote.getTickerByBook(book).also {
                local.saveBookTicker(it)
            }
        } catch (e:Exception) {
            local.getTickerByBook(book)
        }
    }

    override suspend fun saveBookTicker(ticker: Ticker) {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        return try {
            remote.getOrdersByBook(book).also {
                local.saveBookOrders(it.copy(book = book))
            }
        } catch (e:Exception) {
            local.getOrdersByBook(book)
        }
    }

    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        TODO("Not yet implemented")
    }


}