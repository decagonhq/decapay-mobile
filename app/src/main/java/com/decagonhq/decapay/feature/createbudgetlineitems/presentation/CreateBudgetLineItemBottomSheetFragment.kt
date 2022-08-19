package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import android.os.Bundle
import android.util.Log
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
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentCreateBudgetLineItemBottomSheetBinding
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.CategoryItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        // call the budgetcategory
        getBudgetCategoryListViewModel.getBudgetCategoryList()

        // observers
        initObserver()
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
                            val budgetCategoryList = ArrayList<String>()
                            val categories = it.data.data
                            if (categories != null) {
                                for (item in categories){
                                    if (item != null) {
                                        item.title?.let { category -> budgetCategoryList.add(category) }
                                    }
                                }
                            }
                            val budgetCategoryAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, budgetCategoryList)
                            binding.createBudgetLineItemBottomSheetFragmentCategorySpinner.adapter = budgetCategoryAdapter
                            binding.createBudgetLineItemBottomSheetFragmentCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedCategory = parent?.getItemAtPosition(position).toString()
                                    Log.d(TAG, "${selectedCategory}, the id is: ${id}, here is the position: ${position}")
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //
                                }
                            }
                            Log.d(TAG, "here is the output: ${it.data.message}")
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
