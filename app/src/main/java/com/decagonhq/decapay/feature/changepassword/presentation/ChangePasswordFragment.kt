package com.decagonhq.decapay.feature.changepassword.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.LoginInputValidation
import com.decagonhq.decapay.databinding.FragmentChangePasswordBinding
import com.google.android.material.snackbar.Snackbar

class ChangePasswordFragment : Fragment() {
    /**
     * decalre variables and views
     */
    private val TAG = "CHANGEPASSWORD"
    private var _binding: FragmentChangePasswordBinding? = null
    val binding: FragmentChangePasswordBinding get() = _binding!!
    private var pleaseWaitDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pleaseWaitDialog = showPleaseWaitAlertDialog()
        /** on click of the changePassword button */
        binding.changePasswordFragmentChangePasswordButtonBtn.setOnClickListener {
            /** collect values from the input fields */
            val receivedPassword = binding.changePasswordFragmentPasswordTiedt.text?.trim().toString()
            val receivedNewPassword = binding.changePasswordFragmentNewPasswordTiedt.text?.trim().toString()
            val receivedConfirmPassword = binding.changePasswordFragmentConfirmPasswordTiedt.text?.trim().toString()
            if (!LoginInputValidation.validateUserPassword(receivedPassword) || !LoginInputValidation.validateUserPassword(receivedNewPassword) || LoginInputValidation.validateConfirmPassword(receivedNewPassword, receivedConfirmPassword)) {
                hideKeyboard()
                Snackbar.make(
                    binding.root,
                    getString(R.string.change_password_validation),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // make a network call

                // show dialog
                pleaseWaitDialog?.let { it.show() }
                // hide the keyboard
                hideKeyboard()
            }
        }
        // capture the password
        binding.changePasswordFragmentPasswordTiedt.addTextChangedListener {
            val enteredPassword = binding.changePasswordFragmentPasswordTiedt.text.toString()
            onPasswordTextChanged(enteredPassword)
        }
        // capture the new password
        binding.changePasswordFragmentNewPasswordTiedt.addTextChangedListener {
            val enteredNewPassword = binding.changePasswordFragmentNewPasswordTiedt.text.toString()
            onNewPasswordTextChanged(enteredNewPassword)
        }
        // capture the confirm password
        binding.changePasswordFragmentConfirmPasswordTiedt.addTextChangedListener {
            val enteredConfirmPassword = binding.changePasswordFragmentConfirmPasswordTiedt.text.toString()
            val enteredNewPassword = binding.changePasswordFragmentNewPasswordTiedt.text.toString()
            onConfirmPasswordTextChange(enteredNewPassword, enteredConfirmPassword)
        }
    }

    private fun onPasswordTextChanged(receivedPassword: String) {
        if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password cannot be empty") {
            binding.changePasswordFragmentPasswordTil.error = "Password cannot be empty"
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must have a minimum of 8 characters.") {
            binding.changePasswordFragmentPasswordTil.error = "Password must have a minimum of 8 characters."
        } else {
            binding.changePasswordFragmentPasswordTil.error = ""
        }
    }

    private fun onNewPasswordTextChanged(receivedPassword: String) {
        if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password cannot be empty") {
            binding.changePasswordFragmentNewPasswordTil.error = "Password cannot be empty"
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must have a minimum of 8 characters.") {
            binding.changePasswordFragmentNewPasswordTil.error = "Password must have a minimum of 8 characters."
        } else {
            binding.changePasswordFragmentNewPasswordTil.error = ""
        }
    }

    private fun onConfirmPasswordTextChange(newPassword: String, confirmPassword: String) {
        if (!LoginInputValidation.validateConfirmPassword(newPassword, confirmPassword)) {
            binding.changePasswordFragmentConfirmPasswordTil.error = "Invalid Password Entered"
        } else {
            binding.changePasswordFragmentConfirmPasswordTil.error = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
