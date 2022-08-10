package com.decagonhq.decapay.feature.createbudget.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.fragmenttestutils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CreateBudgetFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun createBudgetFragment_launchFragment() {
        launchFragmentInHiltContainer<CreateBudgetFragment>()
        onView(withId(R.id.create_budget_fragment_title_amount_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_title_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_title_amount_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_title_budget_period_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_buget_period_spinner))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_title_description_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_description_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_fragment_done_button_btn))
            .check(matches(isDisplayed()))
    }
}
