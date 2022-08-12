package com.decagonhq.decapay.feature.budgetdetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentBudgetDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BudgetDetailsFragment : Fragment() {

    private val TAG = "BUDGETDETAILSFRAG"
    private var _binding: FragmentBudgetDetailsBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var preference: Preferences
    private val budgetDetailsViewModel: BudgetDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val budgetID = arguments?.getInt("BUDGET_ITEM")
//        Log.d(TAG, "budgetId content: ${budgetID}")
//        budgetDetailsViewModel.getBudgetDetails(budgetID!!)
//        val budget = arguments?.let { it.getSerializable("BUDGET_ITEM") as Content }
//        budget?.let { budgetDetailsViewModel.getBudgetDetails(it.id) }

        val budgetId = arguments?.getInt("BUDGET_ID")
        if (arguments?.getSerializable("BUDGET_ITEM") == null) {
            val budgetId = arguments?.getInt("NEW_BUDGET_ID")
            budgetDetailsViewModel.getBudgetDetails(budgetId!!)
        } else if (arguments?.getInt("NEW_BUDGET_ID") == null) {
            val budget = arguments?.let { it.getSerializable("BUDGET_ITEM") as Content }
            budget?.let { budgetDetailsViewModel.getBudgetDetails(it.id) }
        }

//        val budgetId = arguments?.getInt("NEW_BUDGET_ID")
//        budgetDetailsViewModel.getBudgetDetails(budgetId!!)

        initObserver()




        if (budgetId != null) {
            budgetDetailsViewModel.getBudgetDetails(budgetId)
        }


    }


    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetDetailsViewModel.budgetDetailsResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            val budgetDetails = it.data.data;

                            binding.budgetDetailsHeaderTitleTv.text = budgetDetails.title
                            binding.budgetDetailsHeaderAmountTv.text = budgetDetails.displayProjectedAmount
                            binding.budgetDetailsTasAmountTv.text = budgetDetails.displayTotalAmountSpentSoFar
                            binding.budgetDetailsPercentageAmountTv.text = budgetDetails.displayPercentageSpentSoFar

                            val formatter = SimpleDateFormat("yyyy.MM.dd, HH:mm")

                            formatter.isLenient = false
                            val startDate = budgetDetails.startDate.replace('-', '.')
                            val endDate = budgetDetails.endDate.replace('-', '.')

                            val startTime = "$startDate, 00:00"
                            val endTime = "$endDate, 00:00"
                            val startFormattedDate: Date = formatter.parse(startTime) as Date
                            val endFormattedDate: Date = formatter.parse(endTime) as Date
                            val startDateTimeMillis = startFormattedDate.time
                            val endDateTimiMillis = endFormattedDate.time

                            binding.budgetDetailsCalendarCv.maxDate = endDateTimiMillis
                            binding.budgetDetailsCalendarCv.minDate = startDateTimeMillis

                            // binding.budgetDetailsCalendarCv.d
                        }
                        is Resource.Error -> {
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
}
