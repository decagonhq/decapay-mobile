package com.decagonhq.decapay.feature.editbudget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.BudgetPeriodConstant
import com.decagonhq.decapay.common.utils.converterhelper.convertLongToTime
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.databinding.FragmentEditBudgetBinding
import com.decagonhq.decapay.feature.createbudget.data.staticdata.BudgetPeriods
import com.decagonhq.decapay.feature.createbudget.data.staticdata.CalendarMonth
import com.decagonhq.decapay.feature.createbudget.data.staticdata.YearList
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditBudgetFragment : Fragment() {
    /**
     * decclare views and variables
     */
    private val TAG = "EDITBUDGETFRAGMENT"
    lateinit var editBudgetPeriodType: String
    lateinit var editAnnualPeriodYear: String
    lateinit var editMonthlyPeriodYear: String
    lateinit var editMonthlyPeriodMonth: String
    lateinit var editWeeklyStartDate: String
    lateinit var editDailyStartDateSelected: String
    lateinit var editCustomSelectedDate: String
    lateinit var editCustomeBudgetStartDate: String
    lateinit var editCustomBudgetEndDate: String
    private var pleaseWaitDialog: AlertDialog? = null
    private var _binding: FragmentEditBudgetBinding? = null
    private val binding: FragmentEditBudgetBinding get() = _binding!!
    private val fetchUserBudgetToEditViewModel: FetchUserBudgetToEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBudgetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditBudgetBinding.bind(view)
        // intialize views and variables
        pleaseWaitDialog = showPleaseWaitAlertDialog()
        pleaseWaitDialog?.let { it.show() }
        // here is the selected budget item
        val budgetId = arguments?.getInt("BUDGET_ID")
        // make the network call
        fetchUserBudgetToEditViewModel.showUserBudgetDetailsToEdit(budgetId!!)

        // populate the budget period spinner
        val editBudgetPeriodsAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, BudgetPeriods.budgetPeriod)
        editBudgetPeriodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editBudgetFragmentBugetPeriodSpinner.adapter = editBudgetPeriodsAdapter
        binding.editBudgetFragmentBugetPeriodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent?.getItemAtPosition(position) == "Please select a period") {
                    //
                } else {
                    // on click of an item,
                    editBudgetPeriodType = parent?.getItemAtPosition(position).toString()
                    when (editBudgetPeriodType) {
                        "Annual" -> {
                            // make annual view visible
                            binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            editAnnualSpinnerAdapterInit()
                        }
                        "Monthly" -> {
                            binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            editMonthlyYearSpinner()
                            editMonthlyMonthSpinner()
                        }
                        "Weekly" -> {
                            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            // on click on the start date
                            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.setOnClickListener {
                                editWeeklyStartDate()
                            }
                        }
                        "Daily" -> {
                            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            // on click on the daily view
                            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.setOnClickListener {
                                editDailyDate()
                            }
                        }
                        "Custom" -> {
                            binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.VISIBLE
                            binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                            // on click on the custom view
                            binding.editBudgetFragmentBudgetPeriodCustomTv.setOnClickListener {
                                editShowDateRange()
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }

        // navigate to the BudgetListFragment
        binding.editBudgetFragmentBackNavigationIv.setOnClickListener {
            findNavController().navigate(R.id.budgetListFragment)
        }

        // observe the response
        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                fetchUserBudgetToEditViewModel.fetchEditBudgetResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            // set the budget values to views
                            // first find the type of budget period
                            val receivedBudgetPeriod = it.data.data?.period
                            when (receivedBudgetPeriod) {
                                BudgetPeriodConstant.ANNUAL -> {
                                    // set the budget period spinner to annual
                                    binding.editBudgetFragmentBugetPeriodSpinner.setSelection(
                                        BudgetPeriods.budgetPeriod.indexOf("Annual")
                                    )
                                    // make the appropriate view visible
                                    binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE

                                    binding.editBudgetFragmentTitleTiedt.setText(it.data.data.title)
                                    it.data.data.amount?.let { it1 ->
                                        binding.editBudgetFragmentAmountTiedt.setText(
                                            it1.toString()
                                        )
                                        binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.setSelection(
                                            BudgetPeriods.budgetPeriod.indexOf("Annual")
                                        )
                                        binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.setSelection(
                                            YearList.yearList.indexOf(it.data.data.year.toString())
                                        )
                                        binding.editBudgetFragmentDescriptionTiedt.setText(it.data.data.description)
                                    }
                                }
                                BudgetPeriodConstant.MONTHLY -> {
                                    // set the budget period spinner to monthly
                                    binding.editBudgetFragmentBugetPeriodSpinner.setSelection(
                                        BudgetPeriods.budgetPeriod.indexOf("Monthly")
                                    )
                                    // make the appropriate view visible
                                    binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE

                                    binding.editBudgetFragmentTitleTiedt.setText(it.data.data.title)
                                    binding.editBudgetFragmentAmountTiedt.setText(it.data.data.amount.toString())
                                    binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.setSelection(
                                        YearList.yearList.indexOf(it.data.data.year.toString())
                                    )
                                    binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.setSelection(
                                        CalendarMonth.calendarMonth.indexOf(it.data.data.month.toString())
                                    )
                                    binding.editBudgetFragmentDescriptionTiedt.setText(it.data.data.description)
                                }
                                BudgetPeriodConstant.WEEKLY -> {
                                    // set the budget period spinner to weekly
                                    binding.editBudgetFragmentBugetPeriodSpinner.setSelection(
                                        BudgetPeriods.budgetPeriod.indexOf("Weekly")
                                    )
                                    // make the appropriate view visible
                                    binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE

                                    binding.editBudgetFragmentTitleTiedt.setText(it.data.data.title)
                                    binding.editBudgetFragmentAmountTiedt.setText(it.data.data.amount.toString())
                                    binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.text = it.data.data.budgetStartDate
                                    binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.setText(it.data.data.duration.toString())
                                    binding.editBudgetFragmentDescriptionTiedt.setText(it.data.data.description)
                                }
                                BudgetPeriodConstant.DAILY -> {
                                    // set the budget period spinner to daily
                                    binding.editBudgetFragmentBugetPeriodSpinner.setSelection(
                                        BudgetPeriods.budgetPeriod.indexOf("Daily")
                                    )
                                    // make the appropriate view visible
                                    binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE

                                    binding.editBudgetFragmentTitleTiedt.setText(it.data.data.title)
                                    binding.editBudgetFragmentAmountTiedt.setText(it.data.data.amount.toString())
                                    binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.text = it.data.data.budgetStartDate
                                    binding.editBudgetFragmentDescriptionTiedt.setText(it.data.data.description)
                                }
                                BudgetPeriodConstant.CUSTOM -> {
                                    // set the budget period spinner to custom
                                    binding.editBudgetFragmentBugetPeriodSpinner.setSelection(
                                        BudgetPeriods.budgetPeriod.indexOf("Custom")
                                    )
                                    // make the appropriate view visible
                                    binding.editBudgetFragmentBudgetPeriodCustomTv.visibility = View.VISIBLE
                                    binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                                    binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE

                                    binding.editBudgetFragmentTitleTiedt.setText(it.data.data.title)
                                    binding.editBudgetFragmentAmountTiedt.setText(it.data.data.amount.toString())
                                    binding.editBudgetFragmentBudgetPeriodCustomTv.text = "${it.data.data.budgetStartDate}, ${it.data.data.budgetEndDate}"
                                    binding.editBudgetFragmentDescriptionTiedt.setText(it.data.data.description)
                                }
                            }
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun editAnnualSpinnerAdapterInit() {
        val annualPeriodAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, YearList.yearList)
        annualPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.adapter = annualPeriodAdapter
        binding.editBudgetFragmentBudgetPeriodAnnualYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                editAnnualPeriodYear = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun editMonthlyYearSpinner() {
        val monthPeriodYearAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, YearList.yearList)
        monthPeriodYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.adapter = monthPeriodYearAdapter
        binding.editBudgetFragmentBudgetPeriodMonthlyYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                editMonthlyPeriodYear = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun editMonthlyMonthSpinner() {
        val monthPeriodMonthAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, CalendarMonth.calendarMonth)
        monthPeriodMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.adapter = monthPeriodMonthAdapter
        binding.editBudgetFragmentBudgetPeriodMonthlyMonthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                editMonthlyPeriodMonth = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun editWeeklyStartDate() {
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
            editWeeklyStartDate = "${convertLongToTime(dateSelected)}"
            binding.editBudgetFragmentBudgetPeriodWeeklyStartDateTv.text = editWeeklyStartDate
        }
    }

    private fun editDailyDate() {
        val dailyStartDate = MaterialDatePicker
            .Builder.datePicker()
            .setTitleText("Select Date")
            .build()
        dailyStartDate.show(
            parentFragmentManager,
            "daily_date_picker"
        )
        dailyStartDate.addOnPositiveButtonClickListener { dateSelected ->
            editDailyStartDateSelected = "${convertLongToTime(dateSelected)}"
            binding.editBudgetFragmentBudgetPeriodDailyStartDateTv.text = editDailyStartDateSelected
        }
    }

    private fun editShowDateRange() {
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
                editCustomSelectedDate = "${convertLongToTime(startDate)}, ${convertLongToTime(endDate)}"
                editCustomeBudgetStartDate = convertLongToTime(startDate)
                editCustomBudgetEndDate = convertLongToTime(endDate)
                binding.editBudgetFragmentBudgetPeriodCustomTv.text = editCustomSelectedDate
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
