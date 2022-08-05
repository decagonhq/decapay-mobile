package com.decagonhq.decapay.feature.createbudget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.common.utils.bottomsheetcommunicationonclickinterface.BottomSheetOnclickInterface
import com.decagonhq.decapay.databinding.FragmentCreateBudgetBinding
import com.decagonhq.decapay.feature.createbudget.presentation.bottomsheet.OptionModalBottomSheetFragment
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
        budgetPeriod = binding.createBudgetFragmentBudgetPeriodTv

        // navigate to list of Budget
        binding.createBudgetFragmentBackNavigationIv.setOnClickListener {
            Snackbar.make(
                binding.root,
                "navigate me to Budget list",
                Snackbar.LENGTH_LONG
            ).show()
        }
        // put a click listerner on the budget period
        binding.createBudgetFragmentBudgetPeriodTv.setOnClickListener {
            OptionModalBottomSheetFragment(this).show(
                requireActivity().supportFragmentManager, "optionBottomSheet"
            )
        }
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
