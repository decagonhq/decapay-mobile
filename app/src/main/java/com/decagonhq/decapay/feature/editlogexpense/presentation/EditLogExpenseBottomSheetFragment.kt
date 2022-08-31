package com.decagonhq.decapay.feature.editlogexpense.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.converterhelper.showDateRange
import com.decagonhq.decapay.databinding.FragmentEditLogExpenseBottomSheetBinding
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

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
    private var expenseId by Delegates.notNull<Int>()
    private val editLogExpenseViewModel: EditLogExpenseViewModel by viewModels()
    private lateinit var userSelectedTransactionDate: String
    private lateinit var presentTransactionDate: TextView

    @Inject
    lateinit var editLogExpensePreference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // receive the data from bundle
        val selectedExpenseItem = arguments?.getSerializable(DataConstant.EXPENSE_DATA) as ExpenseContent
        amountSpent = selectedExpenseItem.displayAmount
        description = selectedExpenseItem.description
        transactionDate = selectedExpenseItem.displayTransactionDate
        expenseId = selectedExpenseItem.id
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
        // initialize view
        presentTransactionDate = binding.editLogExpenseeBottomSheetFragmentTransactionDateTv
        val viewId = R.id.editLogExpensee_bottom_sheet_fragment_transaction_date_tv
//
        // set the values to the editLogExpense view
        binding.editLogExpenseBottomSheetFragmentAmountTiedt.setText(amountSpent)
        binding.editLogExpenseBottomSheetFragmentDescriptionTiedt.setText(description)
        binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.text = transactionDate
        binding.editLogExpenseBottomSheetFragmentCategoryTitleTv.text = editLogExpensePreference.getExpenseCategoryTitle()

        // on click on calendar view for user to select date
        binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.setOnClickListener {
            showDateRange(editLogExpensePreference.getBudgetStartDate(), editLogExpensePreference.getBudgetEndDate(), presentTransactionDate, viewId)
        }

        // on click update button
        binding.editLogExpenseeBottomSheetFragmentUpdateButtonBtn.setOnClickListener {
            // capture all the input fields
            val receivedAmount = binding.editLogExpenseBottomSheetFragmentAmountTiedt.text?.trim().toString()
            val receivedDescription = binding.editLogExpenseBottomSheetFragmentDescriptionTiedt.text.toString()
            val receivedTransactionDate = binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.text.trim().toString()
            // validate input field
            if (receivedAmount.isEmpty() || receivedDescription.isEmpty() || receivedTransactionDate.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Input fields cannot be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // make a network call
                editLogExpenseViewModel.userUpdateLogedExpense(
                    expenseId,
                    EditLogExpenseRequestBody(receivedAmount.toDouble(), receivedDescription, userSelectedTransactionDate)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
