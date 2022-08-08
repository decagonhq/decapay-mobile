package com.decagonhq.decapay.feature.createbudget.presentation

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentCreateBudgetBinding
import com.decagonhq.decapay.feature.createbudget.data.staticdata.BudgetPeriods
import com.decagonhq.decapay.feature.createbudget.data.staticdata.CalendarMonth
import com.decagonhq.decapay.feature.createbudget.data.staticdata.YearList
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateBudgetFragment : Fragment() {
    /**
     * declare variables and views
     */
    private val TAG = "CREATEBUDGETFRAG"
    private var _binding: FragmentCreateBudgetBinding? = null
    private val binding: FragmentCreateBudgetBinding get() = _binding!!
    private lateinit var budgetPeriod: TextView
    lateinit var annualPeriodYear: String
    lateinit var monthlyPeriodYear: String
    lateinit var monthlyPeriodMonth: String
    lateinit var weeklyStartDate: String
    lateinit var weeklyDuration: String
    lateinit var dailyStartDateSelected: String

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
        _binding = FragmentCreateBudgetBinding.bind(view)
        // initialize view
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
                    val item = parent?.getItemAtPosition(position).toString()
                    when (item) {
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
                            weeklyStartDate()
                            weeklyDuration = binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.toString()
                            Log.d(TAG, "This is the weekly duration chosen: $weeklyDuration")
                        }
                        "Daily" -> {
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                            dailyDate()
                        }
                        "Custom" -> {
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyStartDateTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyDurationEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyStartDateTv.visibility = View.GONE
                        }
                    }
                    // the item view appears,
                    // all other views must disapper by setting visibility to gone
                    // -> so what happens next
                    // -> to capture the input from users
                    // -> I will create the data for the spinners

//                    Snackbar.make(
//                        binding.root,
//                        "${item} is selected",
//                        Snackbar.LENGTH_LONG
//                    ).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
        // navigate to list of Budget
        binding.createBudgetFragmentBackNavigationIv.setOnClickListener {
            Snackbar.make(
                binding.root,
                "navigate me to Budget list",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun annualSpinnerAdapterInit() {
        val annualPeriodAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, YearList.yearList)
        annualPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.adapter = annualPeriodAdapter
        binding.createBudgetFragmentBudgetPeriodAnnualYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                annualPeriodYear = parent?.getItemAtPosition(position).toString()
                Log.d(TAG, "Here is the selected year for the annual period: $annualPeriodYear")
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
                Log.d(TAG, "here is the year for period month: $monthlyPeriodYear")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    private fun monthlyMonthSpinner() {
        val monthPeriodMonthAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, CalendarMonth.calendarMonth)
        monthPeriodMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.adapter = monthPeriodMonthAdapter
        binding.createBudgetFragmentBudgetPeriodMonthlyYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                monthlyPeriodMonth = parent?.getItemAtPosition(position).toString()
                Log.d(TAG, "See monthly month chosen: $monthlyPeriodMonth")
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
        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )
        return format.format(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
