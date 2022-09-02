package com.decagonhq.decapay.feature.editlogexpense.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.converterhelper.UtilsConverter
import com.decagonhq.decapay.common.utils.converterhelper.showTransactionDatePicker
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentEditLogExpenseBottomSheetBinding
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
    private lateinit var selectedEditExpenseDate: TextView

    @Inject
    lateinit var editLogExpensePreference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // receive the data from bundle
        val selectedExpenseItem = arguments?.getSerializable(DataConstant.EXPENSE_DATA) as ExpenseContent
        amountSpent = selectedExpenseItem.amount.toString()
        description = selectedExpenseItem.description
        transactionDate = selectedExpenseItem.transactionDate
        expenseId = selectedExpenseItem.id
        Log.d(TAG, "here is the transaction date: $transactionDate")
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
        selectedEditExpenseDate = binding.editLogExpenseeBottomSheetFragmentTransactionDateTv
        val viewId = R.id.editLogExpensee_bottom_sheet_fragment_transaction_date_tv
//
        // set the values to the editLogExpense view
        binding.editLogExpenseBottomSheetFragmentAmountTiedt.setText(amountSpent)
        binding.editLogExpenseBottomSheetFragmentDescriptionTiedt.setText(description)
        binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.text = UtilsConverter.formatReceivedTransactionDate(transactionDate)
        userSelectedTransactionDate = UtilsConverter.formatReceivedTransactionDate(transactionDate)
        binding.editLogExpenseBottomSheetFragmentCategoryTitleTv.text = editLogExpensePreference.getExpenseCategoryTitle()

        // on click on calendar view for user to select date
        binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.setOnClickListener {
            showTransactionDatePicker(editLogExpensePreference.getBudgetStartDate(), editLogExpensePreference.getBudgetEndDate(), selectedEditExpenseDate, viewId)
            userSelectedTransactionDate = binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.text.trim().toString()
        }

        // on click update button
        binding.editLogExpenseeBottomSheetFragmentUpdateButtonBtn.setOnClickListener {
            // capture all the input fields
            val receivedAmount = binding.editLogExpenseBottomSheetFragmentAmountTiedt.getNumericValue()
            val receivedDescription = binding.editLogExpenseBottomSheetFragmentDescriptionTiedt.text.toString()
            val receivedTransactionDate = binding.editLogExpenseeBottomSheetFragmentTransactionDateTv.text.trim().toString()
            // validate input field
            if (receivedAmount.toString().isEmpty() || receivedDescription.isEmpty() || receivedTransactionDate.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Input fields cannot be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // make a network call
                editLogExpenseViewModel.userUpdateLogedExpense(
                    expenseId,
                    EditLogExpenseRequestBody(receivedAmount, receivedDescription, userSelectedTransactionDate)
                )
            }
        }

        initObserver()

        // close the bottomSheet
        binding.editLogExpenseBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                editLogExpenseViewModel.updateLogExpenseResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.editLogExpenseBottomSheetFragmentErrorMessageTv.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                "${it.data.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().previousBackStackEntry?.savedStateHandle?.set(DataConstant.NEW_LINE_ITEM, true)
                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            binding.editLogExpenseBottomSheetFragmentErrorMessageTv.visibility = View.VISIBLE
                            binding.editLogExpenseBottomSheetFragmentErrorMessageTv.text = it.message
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
