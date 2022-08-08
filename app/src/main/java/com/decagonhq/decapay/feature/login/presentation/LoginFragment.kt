package com.decagonhq.decapay.feature.login.presentation

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
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.LoginInputValidation
import com.decagonhq.decapay.databinding.FragmentLoginBinding
import com.decagonhq.decapay.feature.login.data.network.model.LoginRequestBody
import com.decagonhq.decapay.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class LoginFragment : Fragment() {
    /**
     * declare views and variables
     */
    @Inject
    lateinit var preference: Preferences

    private val loginViewModel: LoginViewModel by viewModels()
    private val TAG = "LOGINFRAGMENT"
    private lateinit var receivedEmail: String
    private lateinit var receivedPassword: String
    private var pleaseWaitDialog: AlertDialog? = null
    private var saveLoginCredentialsStatus by Delegates.notNull<Boolean>()
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        // check if login credentials are stored
        if (preference.getUserEmail() != null && preference.getUserPassword() != null) {
            // set it to the input fields
            val userEmail = preference.getUserEmail()
            val password = preference.getUserPassword()
            binding.loginFragmentEmailTextinputedittextEmailTiedt.setText(userEmail)
            binding.loginFragmentPasswordTextinputlayoutPasswordTiedt.setText(password)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        // when the view is visible
        pleaseWaitDialog = showPleaseWaitAlertDialog()

        // to validate the inputs received from the fields
        // email field, password field and the remember-login field
        binding.loginFragmentLoginButtonBtn.setOnClickListener {
            // capture the data from the fields, email, password and checkbox
            receivedEmail = binding.loginFragmentEmailTextinputedittextEmailTiedt.text?.trim().toString()
            receivedPassword = binding.loginFragmentPasswordTextinputlayoutPasswordTiedt.text?.trim().toString()
            saveLoginCredentialsStatus = binding.loginFragmentRememberLoginChk.isChecked

            // check the validation
            if (!LoginInputValidation.validateUserEmail(receivedEmail) || !LoginInputValidation.validateUserPassword(receivedPassword)) {
                hideKeyboard()
                Snackbar.make(
                    binding.root,
                    getString(R.string.email_password_validation_feedback),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // check the "Remember Login" box is checked
                // if "true' save "email' and "password" to sharedpreference
                if (binding.loginFragmentRememberLoginChk.isChecked) {
                    // capture the validated email and password and save it to sharedpreference
                    preference.putUserEmail(receivedEmail)
                    preference.putUserPassword(receivedPassword)
                }
                // perform the network call
                loginViewModel.getUserLoggedIn(LoginRequestBody(receivedEmail, receivedPassword))
                // show dialog
                pleaseWaitDialog?.show()
                // hide the keyboard
                hideKeyboard()
            }
        }

        // capture the input email
        binding.loginFragmentEmailTextinputedittextEmailTiedt.addTextChangedListener {
            receivedEmail = binding.loginFragmentEmailTextinputedittextEmailTiedt.text.toString()
            onEmailTextChanged(receivedEmail)
        }

        // capture the password
        binding.loginFragmentPasswordTextinputlayoutPasswordTiedt.addTextChangedListener {
            receivedPassword = binding.loginFragmentPasswordTextinputlayoutPasswordTiedt.text.toString()
            onPasswordTextChanged(receivedPassword)
        }

        initObserver()
        // navigation
        binding.loginFragmentCreateAccountTv.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        // navigate to forgot password
        binding.loginFragmentForgotPasswordTv.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onEmailTextChanged(receivedEmail: String) {
        if (LoginInputValidation.validateEmailForTextWatcher(receivedEmail) == "Field cannot be empty") {
            binding.loginFragmentEmailTextinputlayoutEmailTil.error = "Field cannot be empty"
        } else if (LoginInputValidation.validateEmailForTextWatcher(receivedEmail) == "Invalid email") {
            binding.loginFragmentEmailTextinputlayoutEmailTil.error = "Invalid email"
        } else {
            binding.loginFragmentEmailTextinputlayoutEmailTil.error = ""
        }
    }

    private fun onPasswordTextChanged(receivedPassword: String) {
        if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password cannot be empty") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password cannot be empty"
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must have a minimum of 8 characters.") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password must have a minimum of 8 characters."
        } else {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = ""
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            // capture the token here
                            val token = it.data.data?.token
                            preference.putToken(token!!)
                            (activity as MainActivity).revealDrawer()
                            findNavController().navigate(R.id.action_loginFragment_to_budgetListFragment)

                            // on successfuly loggedin, navigate to your list of budgets
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog!!.dismiss()
                            Snackbar.make(
                                binding.root,
                                "${it.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            binding.loginFragmentEmailTextinputedittextEmailTiedt.text?.clear()
                            binding.loginFragmentPasswordTextinputlayoutPasswordTiedt.text?.clear()
                        }
                        is Resource.Loading -> {
                            pleaseWaitDialog!!.dismiss()
                        }
                    }
                }
            }
        }
    }
}
