package com.decagonhq.decapay.feature.signup




import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagonhq.decapay.decapayapplication.EntryApplication
import com.decagonhq.decapay.feature.signup.presentation.SignUpFragment

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SignUpFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

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