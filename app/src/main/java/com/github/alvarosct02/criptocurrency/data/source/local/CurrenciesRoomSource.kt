package com.github.alvarosct02.criptocurrency.data.source.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.alvarosct02.criptocurrency.data.ErrorType
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookOrdersEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.TickerEntity

class CurrenciesRoomSource(
    private val appDatabase: AppDatabase
) : CurrenciesLocalSource {


    override fun observeAllBooks(): LiveData<Resource<List<Book>>> {
        return Transformations.map(appDatabase.bookEntityDao().observeAll()) { result ->
            if (result.isNullOrEmpty()) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result.map { book -> BookEntity.toModel(book!!) })
            }
        }
    }

    @WorkerThread
    override suspend fun getAllBooks(): List<Book> {
        return appDatabase.bookEntityDao().getAll().map {
            BookEntity.toModel(it)
        }
    }

    @WorkerThread
    override suspend fun saveAllBooks(books: List<Book>) {
        val bookEntityList = books.map { BookEntity.fromModel(it) }
        appDatabase.bookEntityDao().insertAll(bookEntityList)
    }

    override fun observeTickerByBook(book: String): LiveData<Resource<Ticker>> {
        return Transformations.map(appDatabase.tickerEntityDao().observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(TickerEntity.toModel(result))
            }
        }
    }

    @WorkerThread
    override suspend fun getTickerByBook(book: String): Ticker {
        return TickerEntity.toModel(appDatabase.tickerEntityDao().getById(book))
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        val entity = TickerEntity.fromModel(ticker)
        appDatabase.tickerEntityDao().insert(entity)
    }

    override fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>> {
        return Transformations.map(appDatabase.bookOrdersEntityDao().observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(BookOrdersEntity.toModel(result))
            }
        }
    }

    @WorkerThread
    override suspend fun getOrdersByBook(book: String): BookOrders {
        return BookOrdersEntity.toModel(appDatabase.bookOrdersEntityDao().getById(book))
    }

    @WorkerThread
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        val entity = BookOrdersEntity.fromModel(bookOrders)
        appDatabase.bookOrdersEntityDao().insert(entity)
    }

}