package com.decagonhq.decapay.feature.signup





import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.feature.signup.presentation.SignUpFragment
import com.decagonhq.decapay.fragmenttestutils.launchFragmentInHiltContainer

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SignUpFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp(){
        hiltRule.inject()
    }



    @Test
    fun screen_renders(){
//        val scenario = launchFragmentInHiltContainer<SignUpFragment>{
//
//        }

    }


}