package com.github.alvarosct02.criptocurrency.data.models

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("book") val book: String,
    @SerializedName("volume") val volume: String,
    @SerializedName("high") val high: String,
    @SerializedName("last") val last: String,
    @SerializedName("low") val low: String,
    @SerializedName("vwap") val vwap: String,
    @SerializedName("ask") val ask: String,
    @SerializedName("bid") val bid: String,
    @SerializedName("created_at") val createdAt: String,
)