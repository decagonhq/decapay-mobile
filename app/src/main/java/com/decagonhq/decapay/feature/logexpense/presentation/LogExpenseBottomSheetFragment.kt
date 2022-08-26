package com.decagonhq.decapay.feature.logexpense.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.databinding.FragmentLogExpenseBinding
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
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
    private val logExpenseViewModel: LogExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedBudgetLineItems = arguments?.getSerializable(DataConstant.LOG_EXPENSE_BUDGET_LINE_ITEM_SELECTED) as LineItem
        selectedBudgetId = selectedBudgetLineItems.budgetId
        selectedCategoryId = selectedBudgetLineItems.categoryId
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
