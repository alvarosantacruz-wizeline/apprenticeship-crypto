package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

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
    suspend fun getById(book: String): TickerEntity

    @Query("DELETE FROM TickerEntity")
    suspend fun deleteAll()

}
