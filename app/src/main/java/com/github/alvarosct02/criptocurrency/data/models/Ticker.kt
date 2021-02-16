package com.github.alvarosct02.criptocurrency.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.RoundingMode

@Entity
data class Ticker(
    @SerializedName("book") @PrimaryKey val book: String = "",
    @SerializedName("volume") val volume: String = "",
    @SerializedName("high") val high: String = "",
    @SerializedName("last") val last: String = "",
    @SerializedName("low") val low: String = "",
    @SerializedName("vwap") val vwap: String = "",
    @SerializedName("ask") val ask: String = "",
    @SerializedName("bid") val bid: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("change_24") val change24: String = "",
) {

    val from: String get() = book.split("_").getOrNull(0) ?: ""
    val to: String get() = book.split("_").getOrNull(1) ?: ""

    @Ignore
    val lastPrice: BigDecimal = run {
        val lastDecimal = last.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val result = lastDecimal
        result
    }

    @Ignore
    val dayChangePercentage: BigDecimal = run {
        val lastDecimal = last.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val change24Decimal = change24.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val result = try {
            (lastDecimal.divide(lastDecimal.minus(change24Decimal), 4, RoundingMode.HALF_UP).minus(BigDecimal.ONE))
                .times(BigDecimal.valueOf(100)).setScale(2)
        } catch (e: Exception) {
            BigDecimal.valueOf(100)
        }
        result
    }

    @Ignore
    val buyPrice: String = run {
        val bidDecimal = bid.toBigDecimalOrNull() ?: BigDecimal.ZERO
        bidDecimal.toString()
    }
}
