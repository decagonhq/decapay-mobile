package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentEditBudgetLineItemBinding
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.decagonhq.decapay.feature.createbudgetlineitems.presentation.GetBudgetCategoryListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditBudgetLineItemBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "EDITBUDGETLINEITEM"
    private var _binding: FragmentEditBudgetLineItemBinding? = null
    val binding: FragmentEditBudgetLineItemBinding get() = _binding!!
    private val getBudgetCategoryListViewModel: GetBudgetCategoryListViewModel by viewModels()
    private lateinit var selectedCategory: String
    private var projectedAnount by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedBudgetLineItem = arguments?.getSerializable(DataConstant.SELECTED_BUDGET_LINE_ITEM) as LineItem
        selectedCategory = selectedBudgetLineItem.category
        projectedAnount = selectedBudgetLineItem.budgetId.toDouble()
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
        // get category list
        getBudgetCategoryListViewModel.getBudgetCategoryList()
        // disable the spinner from selection
        binding.editBudgetLineItemBottomSheetFragmentCategorySpinner.isEnabled = false
        // set the projected amount to the editable text input fields
        binding.editBudgetLineItemBottomSheetFragmentAmountTiedt.setText(projectedAnount.toString())

        initObserver()
        // close the bottomsheet
        binding.editBudgetLineItemBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getBudgetCategoryListViewModel.getBudgetCategoryListResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            val budgetCategoryList = ArrayList<String>()
                            val categories = it.data.data
                            if (categories != null) {
                                for (item in categories) {
                                    if (item != null) {
                                        item.title?.let { categoryTitle -> budgetCategoryList.add(categoryTitle) }
                                    }
                                }
                            }
                            val budgetCategoryAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, budgetCategoryList)
                            binding.editBudgetLineItemBottomSheetFragmentCategorySpinner.adapter = budgetCategoryAdapter
                            binding.editBudgetLineItemBottomSheetFragmentCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    //
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //
                                }
                            }
                            binding.editBudgetLineItemBottomSheetFragmentCategorySpinner.setSelection(budgetCategoryList.indexOf(selectedCategory))
                        }
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
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
