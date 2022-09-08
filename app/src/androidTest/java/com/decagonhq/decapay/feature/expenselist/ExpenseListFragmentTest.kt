package com.decagonhq.decapay.feature.expenselist

import androidx.core.os.bundleOf
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.feature.expenseslist.presentation.ExpensesListFragment
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
class ExpenseListFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun screen_renders() {
        val fragmentArgs = bundleOf(DataConstant.BUDGET_ID to 1, DataConstant.CATEGORY_ID to 1, DataConstant.CATEGORY to "Transportation")
        val scenario = launchFragmentInHiltContainer<ExpensesListFragment>(fragmentArgs)
    }
}
