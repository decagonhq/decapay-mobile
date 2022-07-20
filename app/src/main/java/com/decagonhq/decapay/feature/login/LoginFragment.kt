package com.decagonhq.decapay.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.LoginInputValidation
import com.decagonhq.decapay.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class LoginFragment : Fragment() {
    /**
     * declare views and variables
     */
    private val TAG = "LOGINFRAGMENT"
    private lateinit var receivedEmail: String
    private lateinit var receivedPassword: String
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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        // to validate the inputs received from the fields
        // email field, password field and the remember-login field
        binding.loginFragmentLoginButtonBtn.setOnClickListener {
            // capture the data from the fields, email, password and checkbox
            receivedEmail = binding.loginFragmentEmailTextinputedittextEmailTiedt.text?.trim().toString()
            receivedPassword = binding.loginFragmentPasswordTextinputlayoutPasswordTiedt.text?.trim().toString()
            saveLoginCredentialsStatus = binding.loginFragmentRememberLoginChk.isChecked

            // check the validation
            if (!LoginInputValidation.validateUserEmail(receivedEmail) || !LoginInputValidation.validateUserPassword(receivedPassword)) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.email_password_validation_feedback),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // check the "Remember Login" box is checked
                // if "true' save "email' and "password" to sharedpreference
                if (binding.loginFragmentRememberLoginChk.isChecked){
                    // capture the validated email and password and save it to sharedpreference
                }
                // perform the network call
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
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 number.") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password must contain at least 1 number."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 number.") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password must contain at least 1 number."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 upper case character.") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password must contain at least 1 upper case character."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 lower case character.") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password must contain at least 1 lower case character."
        } else if (LoginInputValidation.validatePasswordForTextwatcher(receivedPassword) == "Password must contain at least 1 special character (@#$%&?!).") {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = "Password must contain at least 1 special character (@#$%&?!)."
        } else {
            binding.loginFragmentPasswordTextinputlayoutPasswordTil.error = ""
        }
    }
}
