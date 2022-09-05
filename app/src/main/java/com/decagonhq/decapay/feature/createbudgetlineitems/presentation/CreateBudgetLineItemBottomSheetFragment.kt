package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentCreateBudgetLineItemBottomSheetBinding
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.CategoryItem
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.presentation.adapter.CategoryItemSpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateBudgetLineItemBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * decclare views and variables
     */
    private val TAG = "CREATELINEITEM"
    private var _binding: FragmentCreateBudgetLineItemBottomSheetBinding? = null
    private val binding: FragmentCreateBudgetLineItemBottomSheetBinding get() = _binding!!
    lateinit var selectedCategory: String
    private val getBudgetCategoryListViewModel: GetBudgetCategoryListViewModel by viewModels()
    private val createBudgetLineItemViewModel: CreateBudgetLineItemViewModel by viewModels()
    private var budgetId: Int? = null
    private var receivedDetailBudgetId: Int? = null
    private lateinit var budgetCategoryListObject: ArrayList<CategoryItem>
    private var budgetCategoryId: Int? = null
    private lateinit var customSpinnerAdapter: CategoryItemSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get the budgetId from bundle
        budgetId = arguments?.getInt(DataConstant.BUDGET_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBudgetLineItemBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBudgetCategoryListViewModel.getBudgetCategoryList()
        // observers
        initObserver()
        // to create a budgetLineItem

        binding.createBudgetLineItemBottomSheetFragmentCreateButtonBtn.setOnClickListener {
            // receive all the inputs
            val receivedAmount = binding.createBudgetLineItemBottomSheetFragmentAmountTiedt.getNumericValue()
            if (receivedAmount.toString().isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Amount field cannot be empty",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {

                if (budgetId != null) {
                    createBudgetLineItemViewModel.userCreateBudgetLineItem(
                        budgetId!!, CreateBudgetLineItemRequestBody(receivedAmount, budgetCategoryId)
                    )
                }
            }
        }

        // create budgetLine Item Observer
        initBudgetLineItemObserver()

        // close the bottomsheet
        binding.createBudgetLineItemBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showCreateCategoryAlertDialog(destinationId: Int) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.createBudgetLineItem_alert_dialog_createCategory_title))
            .setMessage(getString(R.string.createBudgetLineItem_alert_dialog_createCategory_message))
            .setNeutralButton(getString(R.string.createBudgetLineItem_alert_dialog_createCategory_neutral_button)) { dialog, which ->
                // close the dialog
            }
            .setNegativeButton(getString(R.string.createBudgetLineItem_alert_dialog_createCategory_negative_button)) { dialog, which ->
                // close the dialog
            }
            .setPositiveButton(getString(R.string.createBudgetLineItem_alert_dialog_createCategory_positive_button)) { dialog, which ->
                // navigate to category list screen
                findNavController().navigate(destinationId)
            }
            .show()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getBudgetCategoryListViewModel.getBudgetCategoryListResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            budgetCategoryListObject = ArrayList<CategoryItem>()
                            // add the default category name to the budgetCategoryListObject
                            budgetCategoryListObject.add(CategoryItem(0, getString(R.string.defualt_category_name)))
                            val categories = it.data.data
                            if (categories != null) {
                                for (item in categories) {
                                    if (item != null) {
                                        budgetCategoryListObject.add(item)
                                    }
                                }
                            }

                            // for a first time user, showCreateBudgetCategoryAlertDialog
                            if (categories != null) {
                                if (categories.size <= 1) {
                                    // display the create category dialog
                                    val destinationId = R.id.budgetCategoryList
                                    showCreateCategoryAlertDialog(destinationId)
                                } else {
                                    val customCategoryAdapter = CategoryItemSpinnerAdapter(requireContext(), budgetCategoryListObject)
                                    binding.createBudgetLineItemBottomSheetFragmentCategorySpinner.adapter = customCategoryAdapter
                                    binding.createBudgetLineItemBottomSheetFragmentCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            val categoryItemSelected = parent?.selectedItem as CategoryItem
                                            budgetCategoryId = categoryItemSelected.id
                                        }

                                        override fun onNothingSelected(p0: AdapterView<*>?) {
                                            //
                                        }
                                    }
                                }
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun initBudgetLineItemObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createBudgetLineItemViewModel.createBudgetLineItemResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.createBudgetLineItemFragmentErrorMessageTv.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                "${it.data.message}",
                                Toast.LENGTH_LONG
                            ).show()

                            findNavController().previousBackStackEntry?.savedStateHandle?.set(DataConstant.NEW_LINE_ITEM, true)
                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            binding.createBudgetLineItemFragmentErrorMessageTv.visibility = View.VISIBLE
                            binding.createBudgetLineItemFragmentErrorMessageTv.text = it.message
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
