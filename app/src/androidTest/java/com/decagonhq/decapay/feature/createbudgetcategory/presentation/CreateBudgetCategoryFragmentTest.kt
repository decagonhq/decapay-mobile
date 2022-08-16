package com.decagonhq.decapay.feature.createbudgetcategory.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.fragmenttestutils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CreateBudgetCategoryFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun createBudgetCategoryFragment_launchFragment() {
        launchFragmentInHiltContainer<CreateBudgetCategoryFragment>()
        onView(withId(R.id.create_budget_category_fragment_title_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_category_fragment_callout_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_category_fragment_name_category_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_category_fragment_name_category_til))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_category_fragment_name_category_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_budget_category_fragment_name_category_add_button_btn))
            .check(matches(isDisplayed()))
    }
}
