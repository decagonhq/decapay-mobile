package com.decagonhq.decapay.feature.createnewpassword.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentCreateNewPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNewPasswordFragment : Fragment() {

    /**
     * declare views and variables
     */
    private val TAG = "CREATENEWPASSFRAG"
    private val createNewPasswordViewModel: CreateNewPasswordViewModel by viewModels()
    private var _binding: FragmentCreateNewPasswordBinding? = null
    val binding: FragmentCreateNewPasswordBinding get() = _binding!!
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

        // on click of the createNewPassword button
        binding.createNewPasswordFragmentCreateNewPasswordButtonBtn.setOnClickListener {

        }


        // navigate
        binding.createNewPasswordFragmentLoginTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
