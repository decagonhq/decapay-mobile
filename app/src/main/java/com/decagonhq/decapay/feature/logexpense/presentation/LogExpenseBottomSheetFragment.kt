package com.decagonhq.decapay.feature.logexpense.presentation

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
import com.decagonhq.decapay.common.utils.converterhelper.buildDatePickerWithConstraint
import com.decagonhq.decapay.common.utils.converterhelper.buildDateRangeConstraint
import com.decagonhq.decapay.common.utils.converterhelper.convertLongToTime
import com.decagonhq.decapay.common.utils.converterhelper.getCurrencySymbol
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentLogExpenseBinding
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.bundle.LogExpenseData
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.*
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
    private var transactionDate: String? = null
    private val logExpenseViewModel: LogExpenseViewModel by viewModels()
    private lateinit var retrivedCalendarSelectedDate: String
    private lateinit var selectedDateLogExpenseDate: TextView
    private var calendarSelectedDateToLogExpense: String? = null
    private lateinit var enteredTransactionDate: String
    private var startCapturedDate: Long? = null
    private var endCapturedDate: Long? = null
    private lateinit var logExpenseData: LogExpenseData

    @Inject
    lateinit var logExpensePreference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logExpenseData = (arguments?.getSerializable(DataConstant.LOG_EXPENSE_DATA) as LogExpenseData)
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
        // get currency symbol
        val countryCode = logExpensePreference.getCountry()
        val language = logExpensePreference.getLanguage()
        binding.logExpenseBottomSheetFragmentAmountTiedt.setCurrencySymbol(getCurrencySymbol(language, countryCode), true)
        // initialize view
        selectedDateLogExpenseDate = binding.logExpenseBottomSheetFragmentTransactionDateTv
        val viewId = R.id.logExpense_bottom_sheet_fragment_transaction_date_tv

        // set the category
        binding.logExpenseBottomSheetFragmentCategoryTitleTv.text = logExpenseData.category

        // set the calendarSelctedDate
        if (logExpenseData.calendarSelectedDate != null) {
            binding.logExpenseBottomSheetFragmentTransactionDateTv.text = logExpenseData.calendarSelectedDate
            Log.d("addExpense", "calendarSelection content: ${logExpenseData.calendarSelectedDate}")
        }

        // on click on the calender icon
        binding.logExpenseBottomSheetFragmentTransactionDateTv.setOnClickListener {
            showTransactionDatePicker(logExpenseData.startDateCaptured!!, logExpenseData.endDateCaptured!!)
            Log.d(TAG, "this is the selected transaction date: $transactionDate")
        }

        // on click on save button
        binding.logExpenseBottomSheetFragmentSaveButtonBtn.setOnClickListener {
            // capture all the inputs from the input fields
            val amountSpent = binding.logExpenseBottomSheetFragmentAmountTiedt.getNumericValue()
            val description = binding.logExpenseBottomSheetFragmentDescriptionTiedt.text?.trim().toString()
            enteredTransactionDate = binding.logExpenseBottomSheetFragmentTransactionDateTv.text.trim().toString()
            // validate fields
            if (amountSpent.toString().isEmpty() || description.isEmpty() || enteredTransactionDate.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Input fields cannot be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // make a network call
                logExpenseViewModel.userAddExpense(
                    logExpenseData.budgetId!!, logExpenseData.categoryId!!,
                    LogExpenseRequestBody(
                        amountSpent, description, enteredTransactionDate
                    )
                )
            }
        }

        initObserver()

        // close bottomSheet
        binding.logExpenseBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
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
                            findNavController().previousBackStackEntry?.savedStateHandle?.set(DataConstant.UPDATE_UI, true)
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

    /**
     * used to display a valid datepicker date range
     * when the user selects the transactionDate dateppicker view
     * on the edit and Log Expense screen.
     * Validate date range is between budgetstartDate to currentDate
     * @param startDate is budgetStartDate
     * @param endDate is currentDate
     * @param view is the transactionDate textView
     * @param viewId is the id of the transactionDate view
     */
    fun showTransactionDatePicker(startDate: Long, endDate: Long) {
        val calendarConstraint = buildDateRangeConstraint(startDate, endDate)
        val datePicker = buildDatePickerWithConstraint(calendarConstraint)
        datePicker.show(parentFragmentManager, datePicker.toString())
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            transactionDate = "${convertLongToTime(selectedDate)}"
            binding.logExpenseBottomSheetFragmentTransactionDateTv.text = "${convertLongToTime(selectedDate)}"
        }
    }
}
