package com.decagonhq.decapay.feature.usersettings.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
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
class UserSettingsFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun userSettingsFragment_launchFragment() {
        launchFragmentInHiltContainer<UserSettingsFragment>()
    }
}
