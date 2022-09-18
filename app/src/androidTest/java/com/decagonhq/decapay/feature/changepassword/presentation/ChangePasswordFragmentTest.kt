package com.decagonhq.decapay.feature.changepassword.presentation

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
class ChangePasswordFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun changePasswordFragment_launchFragment() {
        launchFragmentInHiltContainer<ChangePasswordFragment>()
        onView(withId(R.id.change_password_fragment_title_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_password_label_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_password_til))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_password_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_new_password_label_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_new_password_til))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_new_password_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_confirm_password_label_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_confirm_password_til))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_confirm_password_tiedt))
            .check(matches(isDisplayed()))
        onView(withId(R.id.change_password_fragment_change_password_button_btn))
            .check(matches(isDisplayed()))
    }
}
