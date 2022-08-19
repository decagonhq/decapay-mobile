package com.decagonhq.decapay.feature.budgetdetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
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
    private var budgetId: Int? = null
    private lateinit var detailsBudgetId: Content

    @Inject
    lateinit var preference: Preferences
    private val budgetDetailsViewModel: BudgetDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this handles the backPress; all navigation moves to list budget
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (shouldInterceptBackpress()) {
                        findNavController().navigate(
                            R.id.budgetListFragment
                        )
                    } else {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            }
        )
    }

    fun shouldInterceptBackpress() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireArguments().containsKey(DataConstant.BUDGET_ID)) {
            budgetId = arguments?.getInt(DataConstant.BUDGET_ID)
            if (budgetId != null) {
                budgetDetailsViewModel.getBudgetDetails(budgetId!!)
            }
        } else {
            detailsBudgetId = arguments?.getSerializable(DataConstant.BUDGET_ITEM) as Content
            if (detailsBudgetId != null) {
                budgetDetailsViewModel.getBudgetDetails(detailsBudgetId.id)
            }
        }

        // to add budgetlineItems
        binding.budgetDetailsFloatingActionButton.setOnClickListener {
            // I have two source of budgetId, budgetId and detailsBudgetId
            // check if null and add to bundle
            val bundle = Bundle()
            if (budgetId != null && detailsBudgetId.id == null) {
                bundle.putInt(DataConstant.BUDGET_ID_BOTTOMSHEET, budgetId!!)
                findNavController().navigate(R.id.createBudgetLineItemBottomSheetFragment, bundle)
            } else {
                bundle.putInt(DataConstant.BUDGET_ITEM_BOTTOMSHEET, detailsBudgetId.id)
                findNavController().navigate(R.id.createBudgetLineItemBottomSheetFragment, bundle)
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetDetailsViewModel.budgetDetailsResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            val budgetDetails = it.data.data

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
