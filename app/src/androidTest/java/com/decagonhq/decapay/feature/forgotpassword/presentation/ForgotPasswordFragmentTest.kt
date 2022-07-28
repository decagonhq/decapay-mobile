package com.decagonhq.decapay.feature.forgotpassword.presentation

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
class ForgotPasswordFragmentTest {

    private val email = "osehiaseehilen@gmail.com"

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun forgotPasswordFragment_testUserEnterValidEmail() {
        launchFragmentInHiltContainer<ForgotPasswordFragment>()
        onView(withId(R.id.forgot_password_fragment_email_textinputedittext_email_tiedt))
            .perform(typeText(email))
        closeSoftKeyboard()
        onView(withId(R.id.forgot_password_fragment_login_button_btn))
            .perform(click())
    }
}
