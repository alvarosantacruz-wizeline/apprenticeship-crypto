package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory

@Dao
interface TickerHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TickerHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entityList: List<TickerHistory>)

    @Query("SELECT * FROM TickerHistory")
    fun observeAll(): LiveData<List<TickerHistory?>>

    @Query("SELECT * FROM TickerHistory")
    suspend fun getAll(): List<TickerHistory>

    @Query("SELECT * FROM TickerHistory WHERE book = :book")
    fun observeById(book: String): LiveData<TickerHistory?>

    @Query("SELECT * FROM TickerHistory WHERE book = :book")
    fun getById(book: String): TickerHistory

    @Query("DELETE FROM TickerHistory")
    suspend fun deleteAll()
}
