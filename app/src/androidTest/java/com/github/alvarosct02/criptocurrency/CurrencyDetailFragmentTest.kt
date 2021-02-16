package com.github.alvarosct02.criptocurrency

import androidx.core.os.bundleOf
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.di.DataModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@UninstallModules(DataModule::class)
@HiltAndroidTest
class CurrencyDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: CurrenciesRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun whenFragmentStartsTheProgressShouldBeDisplayed() {
        val args = bundleOf("bookId" to "BTC_MXN")
//        launchFragmentInHiltContainer<CurrencyDetailFragment>() {
//
//            onView(withId(R.id.pb_ticker))
//                .check(matches(isDisplayed()))
//            onView(withId(R.id.pb_asks))
//                .check(matches(isDisplayed()))
//            onView(withId(R.id.pb_bids))
//                .check(matches(isDisplayed()))
//        }
    }
//
//    @Test
//    fun whenResultComesTickerInfoIsDisplayed() {
//        Thread.sleep(1000)
//
//        onView(withId(R.id.pb_ticker))
//            .check(matches(withEffectiveVisibility(Visibility.GONE)))
//        onView(withId(R.id.cl_ticker))
//            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
//    }
//
//    @Test
//    fun whenResultComesAskListIsDisplayed() {
//        Thread.sleep(1000)
//
//        onView(withId(R.id.pb_asks))
//            .check(matches(withEffectiveVisibility(Visibility.GONE)))
//        onView(withId(R.id.rv_asks))
//            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
//    }
//
//    @Test
//    fun whenResultComesBidsListIsDisplayed() {
//        Thread.sleep(1000)
//
//        onView(withId(R.id.pb_bids))
//            .check(matches(withEffectiveVisibility(Visibility.GONE)))
//        onView(withId(R.id.rv_bids))
//            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
//    }
}
