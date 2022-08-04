package com.decagonhq.decapay.feature.budgetdetails.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentBudgetDetailsBinding
import com.decagonhq.decapay.databinding.FragmentBudgetListBinding
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetListAdapter

class BudgetDetailsFragment : Fragment() {

    private var _binding: FragmentBudgetDetailsBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBudgetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDateTimeMillis =1517164200000 //(29-01-2018)
       val  endDateTimiMillis =1517682600000



        binding.budgetDetailsCalendarCv.maxDate = endDateTimiMillis
        binding.budgetDetailsCalendarCv.minDate = startDateTimeMillis

    }

}