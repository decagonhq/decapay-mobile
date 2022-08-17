package com.decagonhq.decapay.feature.editbudgetcategory.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.decapay.common.utils.validation.inputfieldvalidation.CreateBudgetCategoryInputValidation
import com.decagonhq.decapay.databinding.FragmentEditBudgetCategoryBinding
import com.google.android.material.snackbar.Snackbar

class EditBudgetCategoryFragment : Fragment() {
    /**
     * declare variables and views
     */
    private val TAG = "EDITBUDGETCATEGORY"
    lateinit var nameOfCategory: String
    private var _binding: FragmentEditBudgetCategoryBinding? = null
    private val binding: FragmentEditBudgetCategoryBinding get() = _binding!!

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
        _binding = FragmentEditBudgetCategoryBinding.bind(view)
        // set the previous budget category to the text-filed
        // get the value from the bundle

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
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
