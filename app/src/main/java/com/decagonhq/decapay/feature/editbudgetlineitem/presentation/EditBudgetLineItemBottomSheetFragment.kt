package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.databinding.FragmentEditBudgetLineItemBinding
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.decagonhq.decapay.feature.createbudgetlineitems.presentation.GetBudgetCategoryListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditBudgetLineItemBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "EDITBUDGETLINEITEM"
    private var _binding: FragmentEditBudgetLineItemBinding? = null
    val binding: FragmentEditBudgetLineItemBinding get() = _binding!!
    private lateinit var selectedCategory: String
    private var projectedAnount by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedBudgetLineItem = arguments?.getSerializable(DataConstant.SELECTED_BUDGET_LINE_ITEM) as LineItem
        selectedCategory = selectedBudgetLineItem.category
        projectedAnount = selectedBudgetLineItem.projectedAmount.toDouble()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBudgetLineItemBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set the category value to textview
        binding.editBudgetLineItemCategoryTv.text = selectedCategory

        // set the projected amount to the editable text input fields
        binding.editBudgetLineItemBottomSheetFragmentAmountTiedt.setText(projectedAnount.toString())

        // close the bottomsheet
        binding.editBudgetLineItemBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
