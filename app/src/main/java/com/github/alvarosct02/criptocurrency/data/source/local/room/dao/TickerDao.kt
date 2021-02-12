package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerWithHistory

@Dao
interface TickerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Ticker)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entityList: List<Ticker>)

    @Query("SELECT * FROM Ticker")
    fun observeAll(): LiveData<List<Ticker>?>

    @Transaction
    @Query("SELECT * FROM Ticker")
    fun observeAllWithHistory(): LiveData<List<TickerWithHistory>>

    @Query("SELECT * FROM Ticker")
    suspend fun getAll(): List<Ticker>

    @Query("SELECT * FROM Ticker WHERE book = :book")
    fun observeById(book: String): LiveData<Ticker?>

    @Query("SELECT * FROM Ticker WHERE book = :book")
    fun getById(book: String): Ticker

    @Query("DELETE FROM Ticker")
    suspend fun deleteAll()
}
