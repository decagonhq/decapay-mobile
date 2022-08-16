package com.decagonhq.decapay.feature.createbudgetcategory.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.CreateBudgetCategoryInputValidation
import com.decagonhq.decapay.databinding.FragmentCreateBudgetCategoryBinding
import com.google.android.material.snackbar.Snackbar

class CreateBudgetCategoryFragment : Fragment() {
    /**
     * declare views and variables
     */
    private val TAG = "CREATEBUDGETFRAG"
    private var _binding: FragmentCreateBudgetCategoryBinding? = null
    val binding: FragmentCreateBudgetCategoryBinding get() = _binding!!

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
        _binding = FragmentCreateBudgetCategoryBinding.bind(view)
        // on click on the add button
        binding.createBudgetCategoryFragmentNameCategoryAddButtonBtn.setOnClickListener {
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
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
