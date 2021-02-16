package com.github.alvarosct02.criptocurrency

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.alvarosct02.criptocurrency.ui.currencyDetail.CurrencyDetailFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CurrencyDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        val args = bundleOf("bookId" to "BTC_MXN")
        launchFragmentInHiltContainer<CurrencyDetailFragment>(args, R.style.Theme_CriptoCurrency)
    }

    @Test
    fun whenFragmentStartsTheProgressShouldBeDisplayed() {

        onView(withId(R.id.pb_ticker))
            .check(matches(isDisplayed()))
        onView(withId(R.id.pb_trades))
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
    fun whenResultComesTradeListIsDisplayed() {
        Thread.sleep(1000)

        onView(withId(R.id.pb_trades))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rv_trades))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}
