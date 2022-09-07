package com.decagonhq.decapay.feature.usersettings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.databinding.FragmentUserSettingsBinding
import com.decagonhq.decapay.feature.signup.data.network.model.signupaccountdetails.SignUpAccountDetailsData

class UserSettingsFragment : Fragment() {
    /**
     * declare views and variables
     */
    private var _binding: FragmentUserSettingsBinding? = null
    private val binding: FragmentUserSettingsBinding get() = _binding!!
    private lateinit var signUpAccountDetailsData: SignUpAccountDetailsData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
