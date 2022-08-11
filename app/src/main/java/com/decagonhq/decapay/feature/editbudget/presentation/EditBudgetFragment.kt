package com.decagonhq.decapay.feature.editbudget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentEditBudgetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBudgetFragment : Fragment() {
    /**
     * decclare views and variables
     */
    private var _binding: FragmentEditBudgetBinding? = null
    private val binding: FragmentEditBudgetBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBudgetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditBudgetBinding.bind(view)

        // navigate to the BudgetListFragment
        binding.editBudgetFragmentBackNavigationIv.setOnClickListener {
            findNavController().navigate(R.id.budgetListFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
