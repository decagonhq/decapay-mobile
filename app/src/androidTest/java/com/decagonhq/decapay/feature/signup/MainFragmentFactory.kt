package com.decagonhq.decapay.feature.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.decagonhq.decapay.feature.signup.presentation.SignUpFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SignUpFragmentFactory

@Inject
constructor(
    private val someString: String
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            SignUpFragment::class.java.name -> {
                val fragment = SignUpFragment()
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}