package com.github.alvarosct02.criptocurrency

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.alvarosct02.criptocurrency.ui.currencyList.CurrencyListFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CurrencyListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<CurrencyListFragment>(null, R.style.Theme_CriptoCurrency)
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
