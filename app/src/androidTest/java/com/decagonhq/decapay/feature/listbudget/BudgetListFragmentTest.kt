package com.decagonhq.decapay.feature.listbudget

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.listbudget.presentation.BudgetListFragment
import com.decagonhq.decapay.fragmenttestutils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BudgetListFragmentTest {

    private val email = "omudavid@gmail.com"
    private val password = "Qwerty12"

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun screen_renders() {

        val scenario = launchFragmentInHiltContainer<BudgetListFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.budgetListFragment_create_budget_fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
