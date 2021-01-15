package com.github.alvarosct02.criptocurrency.data.repository

import com.github.alvarosct02.criptocurrency.data.source.CurrenciesSource

//TODO: Create Interface
class CurrenciesRepository(
    private val remote: CurrenciesSource,
    private val local: CurrenciesSource,
) {

}