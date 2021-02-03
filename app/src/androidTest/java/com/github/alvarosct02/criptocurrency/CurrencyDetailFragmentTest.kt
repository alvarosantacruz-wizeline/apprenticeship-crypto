package com.github.alvarosct02.criptocurrency

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.alvarosct02.criptocurrency.ui.currencyDetail.CurrencyDetailFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class CurrencyDetailFragmentTest : KoinTest {

    @Before
    fun setup() {
        val args = bundleOf("bookId" to "BTC_MXN")
        val scenario = launchFragmentInContainer<CurrencyDetailFragment>(
            args,
            themeResId = R.style.Theme_CriptoCurrency
        )
    }

    @Test
    fun whenFragmentStartsTheProgressShouldBeDisplayed() {
        onView(withId(R.id.pb_ticker))
            .check(matches(isDisplayed()))
        onView(withId(R.id.pb_asks))
            .check(matches(isDisplayed()))
        onView(withId(R.id.pb_bids))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenResultComesTickerInfoIsDisplayed() {
        Thread.sleep(1000)

        onView(withId(R.id.pb_ticker))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.cl_ticker))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun whenResultComesAskListIsDisplayed() {
        Thread.sleep(1000)

        onView(withId(R.id.pb_asks))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rv_asks))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun whenResultComesBidsListIsDisplayed() {
        Thread.sleep(1000)

        onView(withId(R.id.pb_bids))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rv_bids))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}
