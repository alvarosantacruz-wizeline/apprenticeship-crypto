package com.github.alvarosct02.criptocurrency

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.alvarosct02.criptocurrency.ui.currencyList.CurrencyListFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class CurrencyListFragmentTest : KoinTest {

    @Before
    fun setup() {
        val args = bundleOf()
        val scenario = launchFragmentInContainer<CurrencyListFragment>(
            args,
            themeResId = R.style.Theme_CriptoCurrency
        )
    }
    @Test
    fun whenFragmentStartsTheProgressShouldBeDisplayed() {

        onView(withId(R.id.pb_currencies))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_currencies))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
    @Test
    fun whenResultComesTheRecyclerShouldBeDisplayed() {
        Thread.sleep(1000)

        onView(withId(R.id.pb_currencies))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rv_currencies))
            .check(matches(isDisplayed()))
    }
}
