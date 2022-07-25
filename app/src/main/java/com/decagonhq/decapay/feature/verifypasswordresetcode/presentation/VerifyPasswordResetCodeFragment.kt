package com.decagonhq.decapay.feature.verifypasswordresetcode.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.databinding.FragmentVerifyPasswordResetCodeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyPasswordResetCodeFragment : Fragment() {
    /**
     * declare views and variables
     */
    private val TAG = "VERIFYPASWRDRSTCODEFRAG"
    private var _binding: FragmentVerifyPasswordResetCodeBinding? = null
    val binding: FragmentVerifyPasswordResetCodeBinding get() = _binding!!

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
        // capture the pin

        binding.verifyPasswordResetCodeFragmentVerifyButtonBtn.setOnClickListener {
            val pin = binding.verifyPasswordResetCodeFragmentPinview.text.toString()
            Log.d(TAG, "here is the pin value: $pin")
            Snackbar.make(
                binding.root,
                "here is the value: ${pin}",
                Snackbar.LENGTH_LONG
            ).show()
            Log.d(TAG, "hello")
            hideKeyboard()
        }

        // navigate
        binding.verifyPasswordResetCodeFragmentNavigateBackIv.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
