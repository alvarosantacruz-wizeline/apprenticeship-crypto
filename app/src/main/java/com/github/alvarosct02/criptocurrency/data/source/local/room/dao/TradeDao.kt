package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.alvarosct02.criptocurrency.Constants.NUMBER_OF_TRADES_TO_DISPLAY
import com.github.alvarosct02.criptocurrency.data.models.Trade

@Dao
interface TradeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Trade)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entityList: List<Trade>)

    @Query("SELECT * FROM Trade WHERE book = :book ORDER BY createdAt LIMIT $NUMBER_OF_TRADES_TO_DISPLAY")
    fun observeById(book: String): LiveData<List<Trade>?>

    @Query("DELETE FROM Trade")
    suspend fun deleteAll()
}
