package com.decagonhq.decapay.feature.createbudget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.bottomsheetcommunicationonclickinterface.BottomSheetOnclickInterface
import com.decagonhq.decapay.databinding.FragmentCreateBudgetBinding
import com.decagonhq.decapay.feature.createbudget.data.staticdata.BudgetPeriods
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBudgetFragment : Fragment(), BottomSheetOnclickInterface {
    /**
     * declare variables and views
     */
    private val TAG = "CREATEBUDGETFRAG"
    private var _binding: FragmentCreateBudgetBinding? = null
    private val binding: FragmentCreateBudgetBinding get() = _binding!!
    private lateinit var budgetPeriod: TextView

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
                            binding.createBudgetFragmentBudgetPeriodAnnualEdittext.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyWeekSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                        }
                        "Monthly" -> {
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearEdittext.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyWeekSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                        }
                        "Weekly" -> {
                            binding.createBudgetFragmentBudgetPeriodWeeklyWeekSpinner.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodWeeklyMonthSpinner.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodWeeklyYearEdittext.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyTv.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                        }
                        "Daily" -> {
                            binding.createBudgetFragmentBudgetPeriodDailyTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyWeekSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.GONE
                        }
                        "Custom" -> {
                            binding.createBudgetFragmentBudgetPeriodCustomTv.visibility = View.VISIBLE
                            binding.createBudgetFragmentBudgetPeriodAnnualEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodMonthlyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyWeekSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyMonthSpinner.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodWeeklyYearEdittext.visibility = View.GONE
                            binding.createBudgetFragmentBudgetPeriodDailyTv.visibility = View.GONE
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
        // put a click listerner on the budget period
//        binding.createBudgetFragmentBudgetPeriodTv.setOnClickListener {
//            OptionModalBottomSheetFragment(this).show(
//                requireActivity().supportFragmentManager, "optionBottomSheet"
//            )
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /** The date selected from the datepicker is set on the budgetPeriod "TextView" */
    override fun passDataFromOptionBottomSheetToCreateBudgetFragment(date: String) {
        budgetPeriod.text = date
    }
}
