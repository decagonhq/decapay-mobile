package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.converterhelper.getCurrencySymbol
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.showInfoMsgSessionExpired
import com.decagonhq.decapay.databinding.FragmentEditBudgetLineItemBinding
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditBudgetLineItemBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "EDITBUDGETLINEITEM"
    private var _binding: FragmentEditBudgetLineItemBinding? = null
    val binding: FragmentEditBudgetLineItemBinding get() = _binding!!
    private var selectedCategory: String? = null
    private var projectedAnount: Double? = null
    private var selectedCategoryId by Delegates.notNull<Int>()
    private var selectedBudgetId by Delegates.notNull<Int>()
    private val editBudgetLineItemViewModel: EditBudgetLineItemViewModel by viewModels()
    @Inject
    lateinit var editBudgetLineItemPreference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedBudgetLineItem = arguments?.getSerializable(DataConstant.SELECTED_BUDGET_LINE_ITEM) as? LineItem
        if (selectedBudgetLineItem != null) {
            selectedCategory = selectedBudgetLineItem.category.toString()
        }
        if (selectedBudgetLineItem != null) {
            projectedAnount = selectedBudgetLineItem.projectedAmount?.toDouble()
        }
        if (selectedBudgetLineItem != null) {
            selectedCategoryId = selectedBudgetLineItem.categoryId!!
        }
        if (selectedBudgetLineItem != null) {
            selectedBudgetId = selectedBudgetLineItem.budgetId!!
        }
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
        // get currency symbol
        val countryCode = editBudgetLineItemPreference.getCountry()
        val language = editBudgetLineItemPreference.getLanguage()
        binding.editBudgetLineItemBottomSheetFragmentAmountTiedt.setCurrencySymbol(getCurrencySymbol(language, countryCode), true)
        // set the category value to textview
        binding.editBudgetLineItemCategoryTv.text = selectedCategory

        // set the projected amount to the editable text input fields
        binding.editBudgetLineItemBottomSheetFragmentAmountTiedt.setText(projectedAnount.toString())

        // on click on update button
        binding.editBudgetLineItemBottomSheetFragmentUpdateButtonBtn.setOnClickListener {
            // get all the inputs
            validate()
            val actualProjectedAmount = binding.editBudgetLineItemBottomSheetFragmentAmountTiedt.getNumericValue()
            // validate the amount field
            if (actualProjectedAmount == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Projected Amount cannot be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // make a network call
                editBudgetLineItemViewModel.updateBudgetLineItem(
                    selectedBudgetId, selectedCategoryId,
                    EditBudgetLineItemRequestBody(actualProjectedAmount)
                )
            }
        }
        initObserveUpdate()
        // close the bottomsheet
        binding.editBudgetLineItemBottomSheetFragmentCloseIconIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validate(){
        if (binding.editBudgetLineItemBottomSheetFragmentAmountTiedt .getNumericValue()<=0.0) {
            binding.editBudgetLineItemBottomSheetFragmentAmountTil.error = "Amount is required"
        }else{

            binding.editBudgetLineItemBottomSheetFragmentAmountTil.error = ""
        }
    }

    private fun initObserveUpdate() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                editBudgetLineItemViewModel.updateBudgetLineItemResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.editBudgetLineItemFragmentErrorMessageTv.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                "${it.data.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().previousBackStackEntry?.savedStateHandle?.set(DataConstant.UPDATE_UI, true)
                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            // check when it is UNAUTHORIZED
                            when (it.message) {
                                "UNAUTHORIZED" -> {
                                    // navigate to login
                                    // show a dialog
                                    findNavController().navigate(R.id.loginFragment)
                                    showInfoMsgSessionExpired()
                                }
                                else -> {
                                    binding.editBudgetLineItemFragmentErrorMessageTv.visibility = View.VISIBLE
                                    binding.editBudgetLineItemFragmentErrorMessageTv.text = it.message
                                }
                            }
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
