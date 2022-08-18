package com.decagonhq.decapay.feature.listbudgetcategories

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.Data
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.SubError
import com.decagonhq.decapay.feature.listbudgetcategories.domain.usecase.BudgetCategoryListUseCase
import com.decagonhq.decapay.feature.listbudgetcategories.presentation.BudgetCategoryListFragment
import com.decagonhq.decapay.feature.listbudgetcategories.presentation.BudgetCategoryViewModel
import com.decagonhq.decapay.fragmenttestutils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BudgetCategoryListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)




    @Test
    fun screen_renders() {
        val scenario = launchFragmentInHiltContainer<BudgetCategoryListFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.budgetCategoryListFragment_create_category_fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


    }
}
