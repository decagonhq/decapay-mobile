package com.decagonhq.decapay.feature.budgetdetails

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.feature.budgetdetails.presentation.BudgetDetailsFragment
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
class BudgetDetailsFragmentTest {


    lateinit var mockContent: Content


    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        mockContent = Content("",
            "",
            "",
            1, 1,
            "", 1,
            "", 1)

        hiltRule.inject()
    }


    @Test
    fun screen_renders() {
        val fragmentArgs = bundleOf("BUDGET_ITEM" to mockContent)
        val scenario = launchFragmentInHiltContainer<BudgetDetailsFragment>(fragmentArgs)
//        Espresso.onView(ViewMatchers.withId(R.id.budgetd))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


    }
}