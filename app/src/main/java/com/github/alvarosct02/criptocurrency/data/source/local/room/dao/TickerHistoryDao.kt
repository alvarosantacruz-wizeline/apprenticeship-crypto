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

    @Query("SELECT * FROM TickerHistory WHERE book = :book AND bucketStartTime > :startTime ORDER BY bucketStartTime")
    fun observeById(book: String, startTime: String): LiveData<List<TickerHistory>?>

    @Query("DELETE FROM TickerHistory")
    suspend fun deleteAll()
}
