package com.decagonhq.decapay.feature.signup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.resource.Validator
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.databinding.FragmentSignUpBinding
import com.decagonhq.decapay.feature.signup.data.network.model.signupaccountdetails.SignUpAccountDetailsData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private var pleaseWaitDialog: AlertDialog? = null
    private lateinit var receivedEmail: String
    private lateinit var receivedFirstName: String
    private lateinit var receivedLastName: String
    private lateinit var receivedPhoneNumber: String
    private lateinit var receivedPassword: String
    private lateinit var receivedConfirmPassword: String
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var signUpAccountDetailsData: SignUpAccountDetailsData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpAccountDetailsData = SignUpAccountDetailsData(null, null, null, null, null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSignUpBinding.bind(view)
        pleaseWaitDialog = showPleaseWaitAlertDialog()

        binding.signUpFragmentLogInTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.signUpFragmentSignUpBtn.setOnClickListener {

            val formValidated = validateAndSendRequest()
            if (formValidated) {
                // on click of submit button, pass the account details to userSettings
                /*
                signUpViewModel.signUp(
                    SignUpRequestBody(
                        firstName = binding.signUpFragmentFirstNameEt.text.toString().trim(),
                        lastName = binding.signUpFragmentLastNameEt.text.toString().trim(),
                        email = binding.signUpFragmentEmailEt.text.toString().trim(),
                        password = binding.signUpFragmentPasswordEt.text.toString().trim(),
                        phoneNumber = binding.signUpFragmentPhoneNumberEt.text.toString().trim(),
                    )

                )

                 */
                val firstName = binding.signUpFragmentFirstNameEt.text.toString().trim()
                val lastName = binding.signUpFragmentLastNameEt.text.toString().trim()
                val email = binding.signUpFragmentEmailEt.text.toString().trim()
                val password = binding.signUpFragmentPasswordEt.text.toString().trim()
                val phoneNumber = binding.signUpFragmentPhoneNumberEt.text.toString().trim()
                signUpAccountDetailsData.firstName = firstName
                signUpAccountDetailsData.lastName = lastName
                signUpAccountDetailsData.email = email
                signUpAccountDetailsData.password = password
                signUpAccountDetailsData.phoneNumber = phoneNumber
                // pass it to bundle
                val bundle = Bundle()
                bundle.putSerializable(DataConstant.SIGNUP_DETAILS, signUpAccountDetailsData)
                findNavController().navigate(R.id.userSettingsFragment, bundle)

                pleaseWaitDialog?.show()
                // when user account is successfully created, navigate to the login
//                findNavController().navigate(R.id.loginFragment)
            } else {
                hideKeyboard()
                Snackbar.make(
                    binding.root,
                    getString(R.string.sign_up_validation_feedback),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        // Capture input

        binding.signUpFragmentFirstNameEt.addTextChangedListener {
            receivedFirstName = binding.signUpFragmentFirstNameEt.text.toString()
            onFirstNameChanged(receivedFirstName)
        }

        binding.signUpFragmentLastNameEt.addTextChangedListener {
            receivedLastName = binding.signUpFragmentLastNameEt.text.toString()
            onLastNameChanged(receivedLastName)
        }

        binding.signUpFragmentPhoneNumberEt.addTextChangedListener {
            receivedPhoneNumber = binding.signUpFragmentPhoneNumberEt.text.toString()
            onPhoneNumberChanged(receivedPhoneNumber)
        }

        binding.signUpFragmentEmailEt.addTextChangedListener {
            receivedEmail = binding.signUpFragmentEmailEt.text.toString()
            onEmailTextChanged(receivedEmail)
        }
        binding.signUpFragmentPasswordEt.addTextChangedListener {
            receivedPassword = binding.signUpFragmentPasswordEt.text.toString()
            onPasswordTextChanged(receivedPassword)
        }

        binding.signUpFragmentPasswordConfirmationEt.addTextChangedListener {
            receivedConfirmPassword = binding.signUpFragmentPasswordConfirmationEt.text.toString()
            onConfirmPasswordChanged(receivedConfirmPassword)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.registerResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.dismiss()
                            findNavController().navigate(R.id.loginFragment)
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.dismiss()
                            Snackbar.make(
                                binding.root,
                                "${it.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
        // navigation
        binding.signUpFragmentLogInTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun onEmailTextChanged(receivedEmail: String) {
        if (Validator.validateEmailForTextWatcher(receivedEmail) == "Field cannot be empty") {
            binding.signUpFragmentEmailIl.error = "Field cannot be empty"
        } else if (Validator.validateEmailForTextWatcher(receivedEmail) == "Invalid email") {
            binding.signUpFragmentEmailIl.error = "Invalid email"
        } else {
            binding.signUpFragmentEmailIl.error = ""
        }
    }

    private fun onPasswordTextChanged(receivedPassword: String) {
        if (Validator.validatePasswordForTextwatcher(receivedPassword) == "Password cannot be empty") {
            binding.signUpFragmentPasswordIl.error = "Password cannot be empty"
        } else if (Validator.validatePasswordForTextwatcher(receivedPassword) == "Password must have a minimum of 8 characters.") {
            binding.signUpFragmentPasswordIl.error = "Password must have a minimum of 8 characters."
        } else if (Validator.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 number.") {
            binding.signUpFragmentPasswordIl.error = "Password must contain at least 1 number."
        } else if (Validator.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 number.") {
            binding.signUpFragmentPasswordIl.error = "Password must contain at least 1 number."
        } else if (Validator.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 upper case character.") {
            binding.signUpFragmentPasswordIl.error =
                "Password must contain at least 1 upper case character."
        } else if (Validator.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 lower case character.") {
            binding.signUpFragmentPasswordIl.error =
                "Password must contain at least 1 lower case character."
        } else {
            binding.signUpFragmentPasswordIl.error = ""
        }
    }

    private fun onConfirmPasswordChanged(confirmPassword: String) {
        if (binding.signUpFragmentPasswordEt.text.toString().trim() != confirmPassword.trim()) {
            binding.signUpFragmentPasswordConfirmationIl.error = "Confirm password doesn\'t match"
        } else {
            binding.signUpFragmentPasswordConfirmationIl.error = ""
        }
    }

    private fun onPhoneNumberChanged(phoneNumber: String) {
        if (!Validator.validatePhoneNumber(phoneNumber)) {
            binding.signUpFragmentPhoneNumberIl.error = "Please enter a valid phone number"
        } else {
            binding.signUpFragmentPhoneNumberIl.error = ""
        }
    }

    private fun onFirstNameChanged(firstName: String) {
        if (!Validator.validateName(firstName)) {
            binding.signUpFragmentFirstNameIl.error = "Please enter a valid name"
        } else {
            binding.signUpFragmentFirstNameIl.error = ""
        }
    }

    private fun onLastNameChanged(lastname: String) {
        if (!Validator.validateName(lastname)) {
            binding.signUpFragmentLastNameIl.error = "Please enter a valid  name"
        } else {
            binding.signUpFragmentLastNameIl.error = ""
        }
    }

    /** Validate function**/
    private fun validateAndSendRequest(): Boolean {
        val isValidEmail = Validator.validateEmail(binding.signUpFragmentEmailEt.text.toString())

        if (!isValidEmail) {
            binding.signUpFragmentEmailIl.error = "Invalid email"
        }

        val isValidFirstName =
            Validator.validateName(binding.signUpFragmentFirstNameEt.text.toString())

        if (!isValidFirstName) {
            binding.signUpFragmentFirstNameIl.error = "Invalid first name"
        }

        val isValidLastName =
            Validator.validateName(binding.signUpFragmentLastNameEt.text.toString())

        if (!isValidLastName) {
            binding.signUpFragmentLastNameIl.error = "Invalid last name"
        }

        val isValidPassword =
            Validator.validatePassword(binding.signUpFragmentPasswordEt.text.toString())

        if (!isValidPassword) {
            binding.signUpFragmentPasswordIl.error = "Invalid password"
        }

        val isValidPhoneNumber =
            Validator.validatePhoneNumber(binding.signUpFragmentPhoneNumberEt.text.toString())

        if (!isValidPassword) {
            binding.signUpFragmentPhoneNumberIl.error = "Invalid phone number"
        }

        val isValidConfirmPassword =
            binding.signUpFragmentPasswordEt.text.toString().trim() == binding.signUpFragmentPasswordConfirmationEt.text.toString()
                .trim() &&
                binding.signUpFragmentPasswordConfirmationEt.text.toString().trim()
                    .isNotEmpty()

        if (!isValidConfirmPassword) {
            binding.signUpFragmentPasswordConfirmationIl.error = "Confirm password must match"
        }

        return (isValidEmail && isValidFirstName && isValidLastName && isValidLastName && isValidPassword && isValidConfirmPassword && isValidPhoneNumber)
    }
}
