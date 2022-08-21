package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
    private var budgetLineItemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get the budgetIds from bundle
        if (requireArguments().containsKey(DataConstant.BUDGET_ID_BOTTOMSHEET)) {
            budgetId = arguments?.getInt(DataConstant.BUDGET_ID_BOTTOMSHEET)
            Log.d(TAG, "see the budgetId: $budgetId")
        } else {
            receivedDetailBudgetId = arguments?.getInt(DataConstant.BUDGET_ITEM_BOTTOMSHEET)
            Log.d(TAG, "This is from the content: $receivedDetailBudgetId")
        }
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
//        selectedCategory = ""
        // call the budgetcategory
        getBudgetCategoryListViewModel.getBudgetCategoryList()
        // observers
        initObserver()
        // to create a budgetLineItem

        binding.createBudgetLineItemBottomSheetFragmentCreateButtonBtn.setOnClickListener {
            // receive all the inputs
            val receivedAmount = binding.createBudgetLineItemBottomSheetFragmentAmountTiedt.text?.trim().toString()
            if (receivedAmount.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Amount field cannot be empty",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                if (budgetId == null) {
                    // make a network call
                    createBudgetLineItemViewModel.userCreateBudgetLineItem(
                        receivedDetailBudgetId!!, CreateBudgetLineItemRequestBody(receivedAmount.toDouble(), budgetLineItemId)
                    )
                } else {
                    createBudgetLineItemViewModel.userCreateBudgetLineItem(
                        budgetId!!, CreateBudgetLineItemRequestBody(receivedAmount.toDouble(), budgetLineItemId)
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

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getBudgetCategoryListViewModel.getBudgetCategoryListResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            budgetCategoryListObject = ArrayList<CategoryItem>()
                            val budgetCategoryList = ArrayList<String>()
                            val categories = it.data.data
                            if (categories != null) {
                                for (item in categories) {
                                    if (item != null) {
                                        item.title?.let { category -> budgetCategoryList.add(category) }
                                        budgetCategoryListObject.add(item)
                                    }
                                }
                            }
                            val budgetCategoryAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, budgetCategoryList)
                            binding.createBudgetLineItemBottomSheetFragmentCategorySpinner.adapter = budgetCategoryAdapter
                            binding.createBudgetLineItemBottomSheetFragmentCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedCategory = parent?.getItemAtPosition(position).toString()
                                    Log.d(TAG, "$selectedCategory, the id is: $id, here is the position: $position")
                                    for (categoryItem in budgetCategoryListObject) {
                                        if (selectedCategory != null) {
                                            if (categoryItem.title == selectedCategory) {
                                                budgetLineItemId = categoryItem.id
                                            }
                                        }
                                    }
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //
                                }
                            }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
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
                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            /*
                            Toast.makeText(
                                requireContext(),
                                "${it.message}",
                                Toast.LENGTH_LONG
                            ).show()

                             */
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

    private fun showToast(message: String) {
        val layout = layoutInflater.inflate(R.layout.custom_toast, null)
        /*
        val toast = Toast(requireActivity()).apply {
            duration = Toast.LENGTH_LONG
            setGravity(Gravity.BOTTOM, 0, 0)
            setText(message)
            view = layout
        }.show()

         */
        val toast = Toast(requireActivity())
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.setText(message)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
