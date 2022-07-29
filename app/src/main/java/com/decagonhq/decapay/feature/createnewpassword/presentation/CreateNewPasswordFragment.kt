package com.decagonhq.decapay.feature.createnewpassword.presentation

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
import androidx.navigation.fragment.navArgs
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.LoginInputValidation
import com.decagonhq.decapay.databinding.FragmentCreateNewPasswordBinding
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewPasswordFragment : Fragment() {

    /**
     * declare views and variables
     */
    @Inject
    lateinit var preference: Preferences
    private val TAG = "CREATENEWPASSFRAG"
    private val createNewPasswordViewModel: CreateNewPasswordViewModel by viewModels()
    private lateinit var receivedNewPassword: String
    private lateinit var receivedConfirmPassword: String
    private var pleaseWaitDialog: AlertDialog? = null
    private var _binding: FragmentCreateNewPasswordBinding? = null
    val binding: FragmentCreateNewPasswordBinding get() = _binding!!
    private val args: CreateNewPasswordFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateNewPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateNewPasswordBinding.bind(view)
        pleaseWaitDialog = showPleaseWaitAlertDialog()
        /** on click of the createNewPassword button */
        binding.createNewPasswordFragmentCreateNewPasswordButtonBtn.setOnClickListener {
            /** collect values from the input fields */
            receivedNewPassword = binding.createNewPasswordFragmentNewPasswordTiedt.text.toString()
            receivedConfirmPassword = binding.createNewPasswordFragmentConfirmPasswordTiedt.text.toString()
            if (!LoginInputValidation.validateUserPassword(receivedNewPassword) || !LoginInputValidation.validateConfirmPassword(receivedNewPassword, receivedConfirmPassword)) {
                hideKeyboard()
                Snackbar.make(
                    binding.root,
                    getString(R.string.email_password_validation_feedback),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // perform network call
                val token = args.token
                createNewPasswordViewModel.getCreateNewPasswordResponse(CreateNewPasswordRequest(receivedConfirmPassword, receivedNewPassword, token))
                // show dialog
                pleaseWaitDialog?.let { it.show() }
                // hide the keyboard
                hideKeyboard()
            }
        }

        // capture the new password
        binding.createNewPasswordFragmentNewPasswordTiedt.addTextChangedListener {
            receivedNewPassword = binding.createNewPasswordFragmentNewPasswordTiedt.text.toString()
            onNewPasswordTextChanged(receivedNewPassword)
        }
        // capture the confirm password
        binding.createNewPasswordFragmentConfirmPasswordTiedt.addTextChangedListener {
            receivedConfirmPassword = binding.createNewPasswordFragmentConfirmPasswordTiedt.text.toString()
            receivedNewPassword = binding.createNewPasswordFragmentNewPasswordTiedt.text.toString()
            onConfirmPasswordTextChange(receivedNewPassword, receivedConfirmPassword)
        }

        initObserver()
        // navigate
        binding.createNewPasswordFragmentLoginTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun onNewPasswordTextChanged(receivedPassword: String) {
        if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password cannot be empty") {
            binding.createNewPasswordFragmentNewPasswordTil.error = "Password cannot be empty"
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must have a minimum of 8 characters.") {
            binding.createNewPasswordFragmentNewPasswordTil.error = "Password must have a minimum of 8 characters."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 number.") {
            binding.createNewPasswordFragmentNewPasswordTil.error = "Password must contain at least 1 number."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 number.") {
            binding.createNewPasswordFragmentNewPasswordTil.error = "Password must contain at least 1 number."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 upper case character.") {
            binding.createNewPasswordFragmentNewPasswordTil.error = "Password must contain at least 1 upper case character."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 lower case character.") {
            binding.createNewPasswordFragmentNewPasswordTil.error = "Password must contain at least 1 lower case character."
        } else {
            binding.createNewPasswordFragmentNewPasswordTil.error = ""
        }
    }

    fun onConfirmPasswordTextChange(newPassword: String, confirmPassword: String) {
        if (!LoginInputValidation.validateConfirmPassword(newPassword, confirmPassword)) {
            binding.createNewPasswordFragmentConfirmPasswordTil.error = "Invalid Password Entered"
        } else {
            binding.createNewPasswordFragmentConfirmPasswordTil.error = ""
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createNewPasswordViewModel.createNewPasswordResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            // navigate to login
                            findNavController().navigate(R.id.loginFragment)
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data?.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
