package com.decagonhq.decapay.feature.forgotpassword.presentation

import android.os.Bundle
import android.util.Log
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
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.LoginInputValidation
import com.decagonhq.decapay.databinding.FragmentForgotPasswordBinding
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    /**
     * declare view and variables
     */
    private val TAG = "FORGOTPASSWORD_FRAG"
    private val forgotPasswordViewModel: ForgotPasswordRepositoryViewModel by viewModels()
    private lateinit var receivedEmail: String
    private var pleaseWaitDialog: AlertDialog? = null
    private var _binding: FragmentForgotPasswordBinding? = null
    val binding: FragmentForgotPasswordBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentForgotPasswordBinding.bind(view)
        pleaseWaitDialog = showPleaseWaitAlertDialog()

        // activate action on reset password button
        binding.forgotPasswordFragmentLoginButtonBtn.setOnClickListener {
            // receive the user email from the input field
            receivedEmail = binding.forgotPasswordFragmentEmailTextinputedittextEmailTiedt.text.toString()
            // check the validation
            if (!LoginInputValidation.validateUserEmail(receivedEmail)) {
                hideKeyboard()
                Snackbar.make(
                    binding.root,
                    getString(R.string.email_validation_feedback),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                hideKeyboard()
                // perform the network call
                forgotPasswordViewModel.activateForgotPassword(ForgotPasswordRequest(receivedEmail))
                // show dialog
                pleaseWaitDialog?.let { it.show() }
            }
        }

        // capture the input email
        binding.forgotPasswordFragmentEmailTextinputedittextEmailTiedt.addTextChangedListener {
            receivedEmail = binding.forgotPasswordFragmentEmailTextinputedittextEmailTiedt.text.toString()
            onEmailTextChanged(receivedEmail)
        }

        initObserver()

        // navigation
        binding.forgotPasswordFragmentNavigateBackIv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun onEmailTextChanged(receivedEmail: String) {
        if (LoginInputValidation.validateEmailForTextWatcher(receivedEmail) == "Field cannot be empty") {
            binding.forgotPasswordFragmentEmailTextinputlayoutEmailTil.error = "Field cannot be empty"
        } else if (LoginInputValidation.validateEmailForTextWatcher(receivedEmail) == "Invalid email") {
            binding.forgotPasswordFragmentEmailTextinputlayoutEmailTil.error = "Invalid email"
        } else {
            binding.forgotPasswordFragmentEmailTextinputlayoutEmailTil.error = ""
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                forgotPasswordViewModel.forgotPasswordResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerifyPasswordResetCodeFragment2(receivedEmail)
                            findNavController().navigate(action)
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
