package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.TickerEntity

@Dao
interface TickerEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TickerEntity)

    @Query("SELECT * FROM TickerEntity WHERE book = :book")
    fun observeById(book: String): LiveData<TickerEntity?>

    @Query("SELECT * FROM TickerEntity WHERE book = :book")
    fun getById(book: String): TickerEntity

    @Query("DELETE FROM TickerEntity")
    suspend fun deleteAll()
}
