package com.decagonhq.decapay.feature.logout

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.R
import com.decagonhq.decapay.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LogOutTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun activity_screen_renders() {
        var scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.main_activity_drawer_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun nav_drawer_opens() {
        var scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.mainActivity_hamburger_ib)).perform(click())

        onView(withId(R.id.main_activity_nav_view_nv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun nav_drawer_closes() {
        var scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.mainActivity_hamburger_ib)).perform(click())

        onView(withId(R.id.main_activity_nav_view_nv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.menu_logout)).perform(click())

        onView(withId(R.id.main_activity_nav_view_nv))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }
}
