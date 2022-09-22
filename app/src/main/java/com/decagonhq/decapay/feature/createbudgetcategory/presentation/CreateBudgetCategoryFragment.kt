package com.decagonhq.decapay.feature.createbudgetcategory.presentation

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
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.hideKeyboard
import com.decagonhq.decapay.common.utils.uihelpers.showInfoMsgSessionExpired
import com.decagonhq.decapay.common.utils.uihelpers.showPleaseWaitAlertDialog
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.CreateBudgetCategoryInputValidation
import com.decagonhq.decapay.databinding.FragmentCreateBudgetCategoryBinding
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.presentation.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateBudgetCategoryFragment : Fragment() {
    /**
     * declare views and variables
     */
    private val TAG = "CREATEBUDGETFRAG"
    private var _binding: FragmentCreateBudgetCategoryBinding? = null
    val binding: FragmentCreateBudgetCategoryBinding get() = _binding!!
    private var pleaseWaitDialog: AlertDialog? = null
    private val creatBudgetCategoryViewModel: CreateBudgetCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBudgetCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).hideDrawer()

        _binding = FragmentCreateBudgetCategoryBinding.bind(view)
        pleaseWaitDialog = showPleaseWaitAlertDialog()
        // on click on the add button
        binding.createBudgetCategoryFragmentNameCategoryAddButtonBtn.setOnClickListener {
            hideKeyboard()
            // receive the value from the input field
            val categoryName = binding.createBudgetCategoryFragmentNameCategoryTiedt.text?.trim().toString()
            // validate input
            if (!CreateBudgetCategoryInputValidation.validateNameOfCategoryItem(categoryName)) {
                Snackbar.make(
                    binding.root,
                    "Item entered must have 3 characters",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                // make the network call
                creatBudgetCategoryViewModel.userCreateBudgetCategory(
                    CreateBudgetCategoryRequestBody(categoryName)
                )
                pleaseWaitDialog?.let { it.show() }
            }
        }
        initObserver()

        binding.createBudgetCategoryFragmentBackNavigationIv.setOnClickListener {
            findNavController().navigate(R.id.budgetCategoryList)
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                creatBudgetCategoryViewModel.createBudgetCategoryCaptureResponse.collect {
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
                            // check when it is UNAUTHORIZED
                            when (it.message) {
                                "UNAUTHORIZED" -> {
                                    // navigate to login
                                    // show a dialog
                                    findNavController().navigate(R.id.loginFragment)
                                    showInfoMsgSessionExpired()
                                }
                                else -> {
                                    Snackbar.make(
                                        binding.root,
                                        "${it.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
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
