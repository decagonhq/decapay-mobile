package com.decagonhq.decapay.feature.edit_profile.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
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
import com.decagonhq.decapay.databinding.FragmentEditProfileBinding
import com.decagonhq.decapay.databinding.FragmentLoginBinding
import com.decagonhq.decapay.databinding.FragmentUserProfileBinding
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.presentation.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    val binding: FragmentEditProfileBinding get() = _binding!!
    private val editProfileViewModel:EditProfileViewModel by viewModels()
    private var pleaseWaitDialog: AlertDialog? = null
    private lateinit var receivedEmail: String
    private lateinit var receivedFirstName: String
    private lateinit var receivedLastName: String
    private lateinit var receivedPhoneNumber: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).hideDrawer()
        _binding = FragmentEditProfileBinding.bind(view)

        val firstName = arguments?.getString(DataConstant.FIRST_NAME)
        val lastName = arguments?.getString(DataConstant.LAST_NAME)
        val email = arguments?.getString(DataConstant.EMAIL)
        val phoneNumber = arguments?.getString(DataConstant.PHONENUMBER)

        binding.editProfileFirstNameEtv.setText(firstName, TextView.BufferType.EDITABLE)
        binding.editProfileLastNameEtv.setText(lastName, TextView.BufferType.EDITABLE)
        binding.editProfileEmailEtv.setText(email, TextView.BufferType.EDITABLE)
        binding.editProfilePhoneNumberEtv.setText(phoneNumber, TextView.BufferType.EDITABLE)



        binding.editProfileFragmentToolbarIv.setOnClickListener{
            findNavController().popBackStack()
        }


        pleaseWaitDialog = showPleaseWaitAlertDialog()
        binding.editProfileFirstNameEtv.addTextChangedListener {
            receivedFirstName = binding.editProfileFirstNameEtv.text.toString()
            onFirstNameChanged(receivedFirstName)
        }

        binding.editProfileLastNameEtv.addTextChangedListener {
            receivedLastName = binding.editProfileLastNameEtv.text.toString()
            onLastNameChanged(receivedLastName)
        }

        binding.editProfileEmailEtv.addTextChangedListener {
            receivedEmail = binding.editProfileEmailEtv.text.toString()
            onEmailTextChanged(receivedEmail)
        }

        binding.editProfilePhoneNumberEtv.addTextChangedListener {
            receivedPhoneNumber = binding.editProfilePhoneNumberEtv.text.toString()
            onPhoneNumberChanged(receivedPhoneNumber)
        }

        binding.editProfileFragmentEditBtn.setOnClickListener {
            submit()
        }

        setUpFlowListener()

    }

    private fun submit(){

        val formValidated = validateAndSendRequest()
        if (formValidated) {
            val firstName = binding.editProfileFirstNameEtv.text.toString().trim()
            val lastName = binding.editProfileLastNameEtv.text.toString().trim()
            val email = binding.editProfileEmailEtv.text.toString().trim()
            val phoneNumber = binding.editProfilePhoneNumberEtv.text.toString().trim()

            val payload = EditProfileRequestBody(firstName,lastName,email,phoneNumber)

            editProfileViewModel.editProfile(payload)

            pleaseWaitDialog?.show()
        } else {
            hideKeyboard()
            Snackbar.make(
                binding.root,
                getString(R.string.sign_up_validation_feedback),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun setUpFlowListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                editProfileViewModel.editProfileResponse.collect {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                it.data.message,
                                Snackbar.LENGTH_LONG
                            ).show()

                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                it.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }



    private fun onFirstNameChanged(firstName: String) {
        if (!Validator.validateName(firstName)) {
            binding.editProfileFirstNameTl.error = "Please enter a valid name"
        } else {
            binding.editProfileFirstNameTl.error = ""
        }
    }

    private fun onLastNameChanged(lastname: String) {
        if (!Validator.validateName(lastname)) {
            binding.editProfileLastNameTl.error = "Please enter a valid  name"
        } else {
            binding.editProfileLastNameTl.error = ""
        }
    }

    private fun onPhoneNumberChanged(phoneNumber: String) {
        if (!Validator.validatePhoneNumber(phoneNumber)) {
            binding.editProfilePhoneNumberTl.error = "Please enter a valid phone number"
        } else {
            binding.editProfilePhoneNumberTl.error = ""
        }
    }

    private fun onEmailTextChanged(receivedEmail: String) {
        if (Validator.validateEmailForTextWatcher(receivedEmail) == "Field cannot be empty") {
            binding.editProfileEmailTl.error = "Field cannot be empty"
        } else if (Validator.validateEmailForTextWatcher(receivedEmail) == "Invalid email") {
            binding.editProfileEmailTl.error = "Invalid email"
        } else {
            binding.editProfileEmailTl.error = ""
        }
    }


    private fun validateAndSendRequest(): Boolean {
        val isValidEmail = Validator.validateEmail(binding.editProfileEmailEtv.text.toString())

        if (!isValidEmail) {
            binding.editProfileEmailTl.error = "Invalid email"
        }

        val isValidFirstName =
            Validator.validateName(binding.editProfileFirstNameEtv.text.toString())

        if (!isValidFirstName) {
            binding.editProfileFirstNameTl.error = "Invalid first name"
        }

        val isValidLastName =
            Validator.validateName(binding.editProfileLastNameEtv.text.toString())

        if (!isValidLastName) {
            binding.editProfileLastNameTl.error = "Invalid last name"
        }



        val isValidPhoneNumber =
            Validator.validatePhoneNumber(binding.editProfilePhoneNumberEtv.text.toString())

        if (!isValidPhoneNumber) {
            binding.editProfilePhoneNumberTl.error = "Invalid phone number"
        }

        return (isValidEmail && isValidFirstName && isValidLastName && isValidLastName && isValidPhoneNumber)

    }
}

