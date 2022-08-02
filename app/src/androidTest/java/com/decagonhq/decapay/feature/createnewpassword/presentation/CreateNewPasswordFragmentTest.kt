package com.decagonhq.decapay.feature.createnewpassword.presentation

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.fragmenttestutils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CreateNewPasswordFragmentTest {

    private val newPassword = "Reforms*01"
    private val confirmPassword = "Reforms*01"

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun createNewPasswordFragment_testUserInputValidEmail() {
        launchFragmentInHiltContainer<CreateNewPasswordFragment>()
        onView(withId(R.id.create_new_password_fragment_new_password_tiedt))
            .perform(typeText(newPassword))
        closeSoftKeyboard()
        onView(withId(R.id.create_new_password_fragment_confirm_password_tiedt))
            .perform(typeText(confirmPassword))
        closeSoftKeyboard()
        onView(withId(R.id.create_new_password_fragment_create_new_password_button_btn))
            .perform(click())
    }
}
