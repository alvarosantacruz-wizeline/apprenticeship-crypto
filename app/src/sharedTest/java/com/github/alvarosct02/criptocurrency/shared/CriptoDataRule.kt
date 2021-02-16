package com.github.alvarosct02.criptocurrency.shared

import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesRoomSource
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.OrdersDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerHistoryDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TradeDao
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CriptoDataRule : TestRule {

    lateinit var repository: DefaultCurrenciesRepository
    lateinit var remote: CurrenciesRemoteSource
    lateinit var local: CurrenciesLocalSource
    val service = Mockito.mock(CurrenciesService::class.java)
    val tickerDao = Mockito.mock(TickerDao::class.java)
    val tradeDao = Mockito.mock(TradeDao::class.java)
    val tickerHistoryDao = Mockito.mock(TickerHistoryDao::class.java)
    val ordersDao = Mockito.mock(OrdersDao::class.java)

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {

            remote = Mockito.spy(CurrenciesRetrofitSource(service))
            local = Mockito.spy(
                CurrenciesRoomSource(
                    tickerDao = tickerDao,
                    tradeDao = tradeDao,
                    tickerHistoryDao = tickerHistoryDao,
                    ordersDao = ordersDao,
                )
            )
            repository = DefaultCurrenciesRepository(
                remote = remote,
                local = local
            )

            base.evaluate()
        }
    }
}
