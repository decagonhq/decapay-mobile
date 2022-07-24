package com.decagonhq.decapay.feature.createnewpassword.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentCreateNewPasswordBinding


class CreateNewPasswordFragment : Fragment() {

    /**
     * declare views and variables
     */
    private var _binding: FragmentCreateNewPasswordBinding? = null
    val binding: FragmentCreateNewPasswordBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}