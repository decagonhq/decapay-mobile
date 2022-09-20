package com.decagonhq.decapay.feature.createbudget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.BudgetPeriodConstant
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.converterhelper.convertLongToTime
import com.decagonhq.decapay.common.utils.converterhelper.getCurrencySymbol
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.showInfoMsgSessionExpired
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.databinding.FragmentCreateBudgetBinding
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.staticdata.BudgetPeriods
import com.decagonhq.decapay.feature.createbudget.data.staticdata.CalendarMonth
import com.decagonhq.decapay.feature.createbudget.data.staticdata.YearList
import com.decagonhq.decapay.presentation.BaseActivity
import com.decagonhq.decapay.presentation.MainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class CreateBudgetFragment : Fragment() {
    /**
     * declare variables and views
     */
    private val TAG = "CREATEBUDGETFRAG"
    private var _binding: FragmentCreateBudgetBinding? = null
    private val binding: FragmentCreateBudgetBinding get() = _binding!!
    private val createBudgetViewModel: CreateBudgetViewModel by viewModels()
    lateinit var annualPeriodYear: String
    lateinit var monthlyPeriodYear: String
    lateinit var monthlyPeriodMonth: String
    lateinit var weeklyStartDate: String
    lateinit var weeklyDuration: String
    lateinit var dailyStartDateSelected: String
    lateinit var customSelectedDate: String
    lateinit var budgetTitle: String
    var budgetAmount by Delegates.notNull<Double>()
    lateinit var budgetPeriodType: String
    lateinit var customeBudgetStartDate: String
    lateinit var customBudgetEndDate: String
    lateinit var budgetDescription: String
    private var pleaseWaitDialog: AlertDialog? = null
    @Inject
    lateinit var createBudgetPreference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBudgetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).hideDrawer()
        // get currency symbol
        val countryCode = createBudgetPreference.getCountry()
        val language = createBudgetPreference.getLanguage()

        binding.createBudgetFragmentAmountTiedt.setCurrencySymbol(getCurrencySymbol(language, countryCode), true)

        _binding = FragmentCreateBudgetBinding.bind(view)
        // initialize view
        pleaseWaitDialog = showPleaseWaitAlertDialog()
//        budgetPeriod = binding.createBudgetFragmentBudgetPeriodTv

        // list of items
        val budgetPeriodAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, BudgetPeriods.budgetPeriod)
        budgetPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createBudgetFragmentBugetPeriodSpinner.adapter = budgetPeriodAdapter
        binding.createBudgetFragmentBugetPeriodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent?.getItemAtPosition(position) == "Please select a period") {
                    //
                } else {
                    // on click of an item,
                    budgetPeriodType = parent?.getItemAtPosition(position).toString()
                    when (budgetPeriodType) {
                        "Annual" -> {
                            // make annual view visible
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            annualSpinnerAdapterInit()
                        }
                        "Monthly" -> {
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            monthlyYearSpinner()
                            monthlyMonthSpinner()
                        }
                        "Weekly" -> {
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            // on click on the start date
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.setOnClickListener {
                                weeklyStartDate()
                            }
                        }
                        "Daily" -> {
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            // on click on the daily view
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.setOnClickListener {
                                dailyDate()
                            }
                        }
                        "Custom" -> {
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            // on click on the custom view
                            binding.createBudgetFragmentBudgetPeriodCustomTv.setOnClickListener {
                                showDateRange()
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
        // on click, createBudgetDoneButton
        binding.createBudgetFragmentDoneButtonBtn.setOnClickListener {
            // capture the input
            budgetTitle = binding.createBudgetFragmentTitleTiedt.text?.trim().toString()
            budgetAmount = binding.createBudgetFragmentAmountTiedt.getNumericValue()
            // budgetPeriodType
            budgetDescription = binding.createBudgetFragmentDescriptionTiedt.text?.trim().toString()
            weeklyDuration = binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.text.trim().toString()

            // check validation
            if (budgetTitle.isEmpty() || budgetAmount.toString().isEmpty() || budgetPeriodType.isEmpty() || budgetDescription.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Please enter appropriate details to create a budget",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // send the input values to backend
                // so there is a conditional based on the period-type
                when (budgetPeriodType) {
                    "Annual" -> {
                        // make this network call
                        createBudgetViewModel.userCreateBudget(
                            CreateBudgetRequestBody(
                                budgetAmount, null, null,
                                budgetDescription, null, null, BudgetPeriodConstant.ANNUAL,
                                budgetTitle, annualPeriodYear.toInt()
                            )
                        )
                        // show dialog
                        pleaseWaitDialog?.show()
                    }
                    "Monthly" -> {
                        // make this network call
                        createBudgetViewModel.userCreateBudget(
                            CreateBudgetRequestBody(
                                budgetAmount, null, null,
                                budgetDescription, null, CalendarMonth.convertMonthStringValueToInt(monthlyPeriodMonth), BudgetPeriodConstant.MONTHLY,
                                budgetTitle, monthlyPeriodYear.toInt()
                            )
                        )
                        // show dialog
                        pleaseWaitDialog?.show()
                    }
                    "Weekly" -> {
                        // make this network call
                        createBudgetViewModel.userCreateBudget(
                            CreateBudgetRequestBody(
                                budgetAmount, null, weeklyStartDate,
                                budgetDescription, weeklyDuration.toInt(), null, BudgetPeriodConstant.WEEKLY,
                                budgetTitle, null
                            )
                        )

                        // show dialog
                        pleaseWaitDialog?.show()
                    }
                    "Daily" -> {
                        // make this network call
                        createBudgetViewModel.userCreateBudget(
                            CreateBudgetRequestBody(
                                budgetAmount, dailyStartDateSelected, dailyStartDateSelected,
                                budgetDescription, null, null, BudgetPeriodConstant.DAILY,
                                budgetTitle, null
                            )
                        )
                        // show dialog
                        pleaseWaitDialog?.show()
                    }
                    "Custom" -> {
                        // make this network call
                        createBudgetViewModel.userCreateBudget(
                            CreateBudgetRequestBody(
                                budgetAmount, customBudgetEndDate, customeBudgetStartDate,
                                budgetDescription, null, null, BudgetPeriodConstant.CUSTOM,
                                budgetTitle, null
                            )
                        )
                        // show dialog
                        pleaseWaitDialog?.show()
                    }
                }
            }
        }
        // observer
        initObserver()

        // navigate to list of Budget
        binding.createBudgetFragmentBackNavigationIv.setOnClickListener {
            findNavController().navigate(R.id.budgetListFragment)
        }
    }

    private fun annualSpinnerAdapterInit() {
        val annualPeriodAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, YearList.yearList)
        annualPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.adapter = annualPeriodAdapter
        binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                annualPeriodYear = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun monthlyYearSpinner() {
        val monthPeriodYearAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, YearList.yearList)
        monthPeriodYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.adapter = monthPeriodYearAdapter
        binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                monthlyPeriodYear = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun monthlyMonthSpinner() {
        val monthPeriodMonthAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, CalendarMonth.calendarMonth)
        monthPeriodMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.adapter = monthPeriodMonthAdapter
        binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                monthlyPeriodMonth = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun weeklyStartDate() {
        val startDatePicker =
            MaterialDatePicker
                .Builder.datePicker()
                .setTitleText("Select Date")
                .build()
        startDatePicker.show(
            parentFragmentManager,
            "date_picker"
        )
        startDatePicker.addOnPositiveButtonClickListener { dateSelected ->
            weeklyStartDate = "${convertLongToTime(dateSelected)}"
            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.text = weeklyStartDate
        }
    }

    private fun dailyDate() {
        val dailyStartDate = MaterialDatePicker
            .Builder.datePicker()
            .setTitleText("Select Date")
            .build()
        dailyStartDate.show(
            parentFragmentManager,
            "daily_date_picker"
        )
        dailyStartDate.addOnPositiveButtonClickListener { dateSelected ->
            dailyStartDateSelected = "${convertLongToTime(dateSelected)}"
            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.text = dailyStartDateSelected
        }
    }

    private fun showDateRange() {
        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()
        dateRangePicker.show(
            parentFragmentManager,
            "date_range_picker"
        )
        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
            val startDate = dateSelected.first
            val endDate = dateSelected.second

            if (startDate != null && endDate != null) {
                customSelectedDate = "${convertLongToTime(startDate)}, ${convertLongToTime(endDate)}"
                customeBudgetStartDate = convertLongToTime(startDate)
                customBudgetEndDate = convertLongToTime(endDate)
                binding.createBudgetFragmentBudgetPeriodCustomTv.text = customSelectedDate
            }
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createBudgetViewModel.createBudgetResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            val budgetId = it.data.data?.id
                            val bundle = Bundle()
                            bundle.putInt("BUDGET_ID", budgetId!!)
                            findNavController().navigate(R.id.budgetDetailsFragment, bundle)
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            // check when it is UNAUTHORIZED
                            when (it.message) {
                                "UNAUTHORIZED" -> {
                                    // navigate to login
                                    // show a dialog
                                    findNavController().navigate(R.id.loginFragment)
                                    showInfoMsgSessionExpired()
                                }
                                else -> {
                                    Snackbar.make(
                                        binding.root,
                                        "${it.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                        is Resource.Loading -> {
                            pleaseWaitDialog?.let { it.dismiss() }
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
