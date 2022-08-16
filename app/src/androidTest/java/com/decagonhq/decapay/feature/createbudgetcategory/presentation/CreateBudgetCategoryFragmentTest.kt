package com.decagonhq.decapay.feature.createbudgetcategory.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
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
    }
}
