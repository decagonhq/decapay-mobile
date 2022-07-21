package com.decagonhq.decapay.feature.signup


import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest

import com.decagonhq.decapay.R
import com.decagonhq.decapay.decapayapplication.EntryApplication
import com.decagonhq.decapay.feature.signup.presentation.SignUpFragment

import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
//@UninstallModules(AppModule::class)
class SignUpFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        hiltRule.inject()
    }


    @Test
    fun screen_renders(){
        val scenario = launchFragmentInHiltContainer<SignUpFragment>{

        }

    }


}