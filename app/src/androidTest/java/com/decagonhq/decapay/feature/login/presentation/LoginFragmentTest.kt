package com.decagonhq.decapay.feature.login.presentation

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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class LoginFragmentTest {

    private val email = "osehiase@gmail.com"
    private val password = "Blessed*01"

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testLaunchLoginFragment() {
        launchFragmentInHiltContainer<LoginFragment>()
    }

    @Test
    fun loginFragment_testUserLoginCredentials() {
        launchFragmentInHiltContainer<LoginFragment>()
        onView(withId(R.id.login_fragment_email_textinputedittext_email_tiedt))
            .perform(typeText(email))
        closeSoftKeyboard()
        onView(withId(R.id.login_fragment_password_textinputlayout_password_tiedt))
            .perform(typeText(password))
        closeSoftKeyboard()
        onView(withId(R.id.login_fragment_login_button_btn))
            .perform(click())
    }
}
