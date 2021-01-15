package com.github.alvarosct02.criptocurrency.data.source.remote.retrofit

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.Ticker

class BitsoWrapper<T>(
    val success: Boolean,
    val payload: T,
)
