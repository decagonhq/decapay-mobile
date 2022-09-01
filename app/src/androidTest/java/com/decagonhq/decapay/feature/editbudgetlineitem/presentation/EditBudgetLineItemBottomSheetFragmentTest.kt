package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

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
class EditBudgetLineItemBottomSheetFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun editBudgetLineItemBottomSheetFragment_launchFragment() {
        launchFragmentInHiltContainer<EditBudgetLineItemBottomSheetFragment>()
        onView(withId(R.id.edit_budget_line_item_bottom_sheet_fragment_category_title_tv))
            .check(matches(isDisplayed()))
//        onView(withId(R.id.edit_budget_line_item_bottom_sheet_fragment_label_category_tv))
//            .check(matches(isDisplayed()))
        onView(withId(R.id.edit_budget_line_item_bottom_sheet_fragment_label_projected_amount_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.edit_budget_line_item_bottom_sheet_fragment_amount_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.edit_budget_line_item_bottom_sheet_fragment_update_button_btn))
            .check(matches(isDisplayed()))
    }
}
