package com.decagonhq.decapay.feature.verifypasswordresetcode.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.databinding.FragmentVerifyPasswordResetCodeBinding
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyPasswordResetCodeFragment : Fragment() {
    /**
     * declare views and variables
     */
    private val TAG = "VERIFYPASWRDRSTCODEFRAG"
    private var pleaseWaitDialog: AlertDialog? = null
    private val verifyPasswordResetCodeViewModel: VerifyPasswordResetCodeViewModel by viewModels()
    private var _binding: FragmentVerifyPasswordResetCodeBinding? = null
    val binding: FragmentVerifyPasswordResetCodeBinding get() = _binding!!
    val args: VerifyPasswordResetCodeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyPasswordResetCodeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVerifyPasswordResetCodeBinding.bind(view)
        pleaseWaitDialog = showPleaseWaitAlertDialog()

        // capture the pin
        binding.verifyPasswordResetCodeFragmentVerifyButtonBtn.setOnClickListener {
            val pin = binding.verifyPasswordResetCodeFragmentPinview.text.toString()
            val email = args.email
            // perform the network call
            verifyPasswordResetCodeViewModel.getUserVerifyPasswordResetCode(
                VerifyPasswordResetCodeRequest(email = email, pin)
            )
            pleaseWaitDialog?.let { it.show() }
            hideKeyboard()
        }

        // navigate
        binding.verifyPasswordResetCodeFragmentNavigateBackIv.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }

        initObserver()
    }

    fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                verifyPasswordResetCodeViewModel.verifyPasswordResetCode.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "You have successfully verified: ${it.messages}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.createNewPasswordFragment)
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.messages}",
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
