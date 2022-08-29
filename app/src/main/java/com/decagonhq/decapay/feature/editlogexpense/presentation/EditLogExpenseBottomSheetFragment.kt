package com.decagonhq.decapay.feature.editlogexpense.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.databinding.FragmentEditLogExpenseBottomSheetBinding
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditLogExpenseBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "EDITLOGEXPENSE"
    private var _binding: FragmentEditLogExpenseBottomSheetBinding? = null
    private val binding: FragmentEditLogExpenseBottomSheetBinding get() = _binding!!
    private lateinit var amountSpent: String
    private lateinit var description: String
    private lateinit var transactionDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // receive the data from bundle
        val selectedExpenseItem = arguments?.getSerializable(DataConstant.EXPENSE_DATA) as ExpenseContent
        amountSpent = selectedExpenseItem.displayAmount
        description = selectedExpenseItem.description
        transactionDate = selectedExpenseItem.displayTransactionDate
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditLogExpenseBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set the values to the editLogExpense view
        binding.editLogExpenseBottomSheetFragmentAmountTiedt.setText(amountSpent)
        binding.editLogExpenseBottomSheetFragmentDescriptionTiedt.setText(description)
        binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.text = transactionDate
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
