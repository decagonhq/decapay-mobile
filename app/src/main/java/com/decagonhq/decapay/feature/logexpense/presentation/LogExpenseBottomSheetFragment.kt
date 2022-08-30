package com.decagonhq.decapay.feature.logexpense.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.converterhelper.convertLongToTime
import com.decagonhq.decapay.common.utils.converterhelper.getTodaysDate
import com.decagonhq.decapay.common.utils.datavalidator.RangeValidator
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentLogExpenseBinding
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class LogExpenseBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "LOGEXPENSE"
    private var _binding: FragmentLogExpenseBinding? = null
    val binding: FragmentLogExpenseBinding get() = _binding!!
    private var selectedBudgetId by Delegates.notNull<Int>()
    private var selectedCategoryId by Delegates.notNull<Int>()
    private lateinit var budgetCategory: String
    private lateinit var transactionDate: String
    private val logExpenseViewModel: LogExpenseViewModel by viewModels()
    private lateinit var retrivedCalendarSelectedDate: String

    @Inject
    lateinit var logExpensePreference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedBudgetLineItems = arguments?.getSerializable(DataConstant.LOG_EXPENSE_BUDGET_LINE_ITEM_SELECTED) as LineItem
        selectedBudgetId = selectedBudgetLineItems.budgetId
        selectedCategoryId = selectedBudgetLineItems.categoryId
        budgetCategory = selectedBudgetLineItems.category
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogExpenseBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set the category
        binding.logExpenseBottomSheetFragmentCategoryTitleTv.text = budgetCategory
        // set currant date to transaction date
        retrivedCalendarSelectedDate = logExpensePreference.getSelectedDate()
        if (retrivedCalendarSelectedDate.isNotBlank()) {
            binding.logExpenseBottomSheetFragmentTransactionDateTv.text = retrivedCalendarSelectedDate
        } else {
            binding.logExpenseBottomSheetFragmentTransactionDateTv.text = getTodaysDate()
        }
        Log.d(TAG,"see the format of the current date: ${getTodaysDate()}")

        // on click on save button
        binding.logExpenseBottomSheetFragmentSaveButtonBtn.setOnClickListener {
            // capture all the inputs from the input fields
            val amountSpent = binding.logExpenseBottomSheetFragmentAmountTiedt.text?.trim().toString()
            val description = binding.logExpenseBottomSheetFragmentDescriptionTiedt.text?.trim().toString()
            val enteredTransactionDate = binding.logExpenseBottomSheetFragmentTransactionDateTv.text.trim().toString()
            // validate fields
            if (amountSpent.isEmpty() || description.isEmpty() || enteredTransactionDate.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Input fields cannot be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // make a network call
                logExpenseViewModel.userAddExpense(
                    selectedBudgetId, selectedCategoryId,
                    LogExpenseRequestBody(
                        amountSpent.toDouble(), description, transactionDate
                    )
                )
            }
        }

        // on click on the calender icon
        binding.logExpenseBottomSheetFragmentTransactionDateTv.setOnClickListener {
            showTheDateRange(logExpensePreference.getBudgetStartDate(), logExpensePreference.getBudgetEndDate())
        }

        initObserver()

        // close bottomSheet
        binding.logExpenseBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
        }
        // on click on cancel button
        binding.logExpenseBottomSheetFragmentCancelButtonBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showTheDateRange(startDate: Long, endDate: Long) {
        val builderRange = MaterialDatePicker.Builder.datePicker()
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val dateValidatorStartDate = DateValidatorPointForward.from(startDate)
        val dateValidatorEndDate = DateValidatorPointBackward.before(endDate)
        val listValidators = ArrayList<CalendarConstraints.DateValidator>()
        listValidators.add(dateValidatorStartDate)
        listValidators.add(dateValidatorEndDate)
        val validators = CompositeDateValidator.allOf(listValidators)
        constraintsBuilderRange.setValidator(validators)
        builderRange.setCalendarConstraints(constraintsBuilderRange.build())
        val pickRange = builderRange.build()
        pickRange.show(parentFragmentManager, pickRange.toString())
        pickRange.addOnPositiveButtonClickListener { selectedDate ->
            transactionDate = "${convertLongToTime(selectedDate)}"
            binding.logExpenseBottomSheetFragmentTransactionDateTv.text = transactionDate
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                logExpenseViewModel.addExpenseResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.logExpenseBottomSheetFragmentErrorMessageTv.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                "${it.data.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            binding.logExpenseBottomSheetFragmentErrorMessageTv.visibility = View.VISIBLE
                            binding.logExpenseBottomSheetFragmentErrorMessageTv.text = it.message
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
