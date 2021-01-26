package com.github.alvarosct02.criptocurrency.data.models

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

data class Ticker(
    @SerializedName("book") val book: String = "",
    @SerializedName("volume") val volume: String = "",
    @SerializedName("high") val high: String = "",
    @SerializedName("last") val last: String = "",
    @SerializedName("low") val low: String = "",
    @SerializedName("vwap") val vwap: String = "",
    @SerializedName("ask") val ask: String = "",
    @SerializedName("bid") val bid: String = "",
    @SerializedName("created_at") val createdAt: String = "",
) {

    val avgPrice: String
        get() {
            val askDecimal = ask.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val bidDecimal = bid.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val result = (askDecimal.plus(bidDecimal)).divide(BigDecimal(2))
//            result.round(MathContext(2, RoundingMode.CEILING))
            return result.toString()
//            return (((ask.toDoubleOrNull() ?: 0.0) + (bid.toDoubleOrNull() ?: 0.0)) / 2).toString()
        }
}