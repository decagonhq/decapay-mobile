package com.decagonhq.decapay.feature.editbudgetcategory.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.CreateBudgetCategoryInputValidation
import com.decagonhq.decapay.databinding.FragmentEditBudgetCategoryBinding
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.Data
import com.decagonhq.decapay.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditBudgetCategoryFragment : Fragment() {
    /**
     * declare variables and views
     */
    private val TAG = "EDITBUDGETCATEGORY"
    lateinit var nameOfCategory: String
    private var _binding: FragmentEditBudgetCategoryBinding? = null
    private val binding: FragmentEditBudgetCategoryBinding get() = _binding!!
    private val editBudgetCategoryViewModel: EditBudgetCategoryViewModel by viewModels()
    private var pleaseWaitDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBudgetCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideDrawer()

        _binding = FragmentEditBudgetCategoryBinding.bind(view)
        pleaseWaitDialog = showPleaseWaitAlertDialog()
        // set the previous budget category to the text-filed
        // get the value from the bundle
        val budgetCategoryData = arguments?.getSerializable(DataConstant.BUDGET_CATEGORY_LIST_ITEM) as Data
        binding.editBudgetCategoryFragmentNameCategoryTiedt.setText(budgetCategoryData.title)

        // on click on the save-button
        binding.editBudgetCategoryFragmentSaveButtonBtn.setOnClickListener {
            // capture values in the input field
            nameOfCategory = binding.editBudgetCategoryFragmentNameCategoryTiedt.text?.trim().toString()
            // validate input field
            if (!CreateBudgetCategoryInputValidation.validateNameOfCategoryItem(nameOfCategory)) {
                Snackbar.make(
                    binding.root,
                    "Item entered must have 3 characters",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // make the network call
                editBudgetCategoryViewModel.updateBudgetCategory(budgetCategoryData.id, EditBudgetCategoryRequestBody(nameOfCategory))
                pleaseWaitDialog?.let { it.show() }
            }
        }

        initObserver()

        // back navigation
        binding.editBudgetCategoryFragmentBackNavigationIv.setOnClickListener {
            findNavController().navigate(R.id.budgetCategoryList)
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                editBudgetCategoryViewModel.editBudgetCategoryResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            pleaseWaitDialog?.let { it.dismiss() }
                            Snackbar.make(
                                binding.root,
                                "${it.data.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.budgetCategoryList)
                        }
                        is Resource.Error -> {
                            pleaseWaitDialog?.let { it.dismiss() }
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
