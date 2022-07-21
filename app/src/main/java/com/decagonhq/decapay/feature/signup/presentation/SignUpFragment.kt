package com.decagonhq.decapay.feature.signup.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.resource.Validator

import com.decagonhq.decapay.databinding.FragmentSignUpBinding
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSignUpBinding.bind(view)

        binding.signUpFragmentSignUpBtn.setOnClickListener {

            val formValidated = validateAndSendRequest()
            if (formValidated) {
                signUpViewModel.signUp(
                    SignUpRequestBody(
                        firstName = binding.signUpFragmentFirstNameEt.text.toString(),
                        lastName = binding.signUpFragmentLastNameEt.text.toString(),
                        email = binding.signUpFragmentEmailEt.text.toString(),
                        password = binding.signUpFragmentPasswordEt.text.toString(),
                        phoneNumber = binding.signUpFragmentPhoneNumberEt.text.toString(),
                        passwordConfirmation = binding.signUpFragmentPasswordConfirmationEt.text.toString(),
                    )

                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.registerResponse.collect {
                    when (it) {
                        is Resource.Success -> {
//
                            Toast.makeText(requireContext(), "Success ${it.data.next}", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
//

                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {

                        }
                        else -> {}
                    }
                }
            }
        }
    }

    /** Validate function**/
    private fun validateAndSendRequest(): Boolean {
        val isValidEmail = Validator.validateEmail(binding.signUpFragmentEmailEt.text.toString())
        if (!isValidEmail) {
            binding.signUpFragmentEmailEt.error = "Please enter a valid email"
        }
        val isValidFirstName =
            Validator.validateName(binding.signUpFragmentFirstNameEt.text.toString())
        if (!isValidFirstName) {
            binding.signUpFragmentFirstNameEt.error = "Please enter a valid name"
        }
        val isValidLastName =
            Validator.validateName(binding.signUpFragmentLastNameEt.text.toString())
        if (!isValidLastName) {
            binding.signUpFragmentLastNameEt.error = "Please enter a valid name"
        }
        val isValidPassword =
            Validator.validatePassword(binding.signUpFragmentPasswordEt.text.toString())
        if (!isValidLastName) {
            binding.signUpFragmentPasswordEt.error = "Password must be at least 6 characters"
        }

        val isValidPhoneNumber =
            Validator.validatePhoneNumber(binding.signUpFragmentPhoneNumberEt.text.toString())
        if (!isValidPhoneNumber) {
            binding.signUpFragmentPhoneNumberEt.error = "Please enter valid phone number"
        }

        val isValidConfirmPassword =
            binding.signUpFragmentPasswordEt.text.toString() == binding.signUpFragmentPasswordConfirmationEt.text.toString()
                    && binding.signUpFragmentPasswordConfirmationEt.text.toString()
                .isNotEmpty()
        if (!isValidConfirmPassword) {
            binding.signUpFragmentPasswordEt.error = "Password must be at least 6 characters"
            binding.signUpFragmentPasswordConfirmationEt.error = "Passwords must match"
        }

        return (isValidEmail && isValidFirstName && isValidLastName && isValidLastName && isValidConfirmPassword && isValidPhoneNumber)
    }

}