package com.github.alvarosct02.criptocurrency.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class TickerWithHistory(
    @Embedded val ticker: Ticker,
    @Relation(
        parentColumn = "book",
        entityColumn = "book"
    )
    val history: List<TickerHistory>
)
