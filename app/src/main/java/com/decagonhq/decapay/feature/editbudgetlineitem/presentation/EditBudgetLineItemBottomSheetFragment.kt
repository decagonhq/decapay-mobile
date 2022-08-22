package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentEditBudgetLineItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditBudgetLineItemBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "EDITBUDGETLINEITEM"
    private var _binding: FragmentEditBudgetLineItemBinding? = null
    val binding: FragmentEditBudgetLineItemBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBudgetLineItemBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
