package com.github.alvarosct02.criptocurrency.data.source.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.alvarosct02.criptocurrency.data.models.Ticker

@Entity
data class TickerEntity(
    @PrimaryKey var book: String = "",
    var volume: String = "",
    var high: String = "",
    var last: String = "",
    var low: String = "",
    var vwap: String = "",
    var ask: String = "",
    var bid: String = "",
    var createdAt: String = "",
) {
    companion object {
        fun fromModel(model: Ticker): TickerEntity {
            return TickerEntity(
                book = model.book,
                volume = model.volume,
                high = model.high,
                last = model.last,
                low = model.low,
                vwap = model.vwap,
                ask = model.ask,
                bid = model.bid,
                createdAt = model.createdAt,
            )
        }

        fun toModel(entity: TickerEntity): Ticker {
            return Ticker(
                book = entity.book,
                volume = entity.volume,
                high = entity.high,
                last = entity.last,
                low = entity.low,
                vwap = entity.vwap,
                ask = entity.ask,
                bid = entity.bid,
                createdAt = entity.createdAt,
            )
        }
    }
}
