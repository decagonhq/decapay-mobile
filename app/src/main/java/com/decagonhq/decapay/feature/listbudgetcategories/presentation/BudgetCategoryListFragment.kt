package com.decagonhq.decapay.feature.listbudgetcategories.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentBudgetCategoryListBinding
import com.decagonhq.decapay.feature.listbudgetcategories.adaptor.CategoryClicker
import com.decagonhq.decapay.feature.listbudgetcategories.adaptor.CategoryListAdaptor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BudgetCategoryListFragment : Fragment(), CategoryClicker {

    private var _binding: FragmentBudgetCategoryListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryListAdaptor
    private val budgetCategoryViewModel : BudgetCategoryViewModel by  viewModels()

    private val list = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFlowListener()

        val testList = mutableListOf<Int>(1, 2, 3, 3, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7)
        list.addAll(testList)
        adapter = CategoryListAdaptor(list, this);
        binding.budgetCategoryListFragmentBudgetCategoryListRv.adapter = adapter
        binding.budgetCategoryListFragmentBudgetCategoryListRv.layoutManager =
            LinearLayoutManager(requireContext())
        setDataLoaded(list);
    }

    private fun setUpFlowListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetCategoryViewModel.budgetCategoryListResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            setIsLoadingScreen()
                        }

                        is Resource.Success -> {

                        }
                        else -> {}
                    }
                }
            }
        }
    }


    private fun showDialog( position:Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.delete_modal_layout)

        val yesBtn = dialog.findViewById(R.id.delete_modal_yes_btn) as Button
        val noBtn = dialog.findViewById(R.id.delete_modal_no_btn) as Button
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        yesBtn.setOnClickListener {
            dialog.dismiss()
            adapter.deleteItemAtIndex(position)
        }
        dialog.show()

    }



    override fun onClickItemEllipsis(currentCategory: Int, position: Int, view: View) {
        showPopupMenu(position, view, currentCategory)
    }

    private fun showPopupMenu(position: Int, view: View, currentCategory: Int) =
        PopupMenu(view.context, view).run {
            menuInflater.inflate(R.menu.category_item_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {

                    }
                    "Delete" -> {
                       showDialog(position)
                    }
                }
                true
            }
            show()
        }


    private fun setIsLoadingScreen() {
        binding.budgetCategoryListFragmentEmptyListIv.visibility = View.GONE
        binding.budgetCategoryListFragmentEmptyListSubheaderTv.visibility = View.GONE
        binding.budgetCategoryListFragmentEmptyListSubheaderTv.visibility = View.GONE
        binding.budgetCategoryListFragmentPageLoadingPb.visibility = View.VISIBLE

    }

    private fun setEmptyListScreen() {
        binding.budgetCategoryListFragmentPageLoadingPb.visibility = View.GONE
        binding.budgetCategoryListFragmentEmptyListIv.visibility = View.VISIBLE
        binding.budgetCategoryListFragmentEmptyListHeaderTv.visibility = View.VISIBLE
        binding.budgetCategoryListFragmentEmptyListSubheaderTv.visibility = View.VISIBLE
    }

    private fun setDataLoaded(newList: MutableList<Int>) {

        if (newList.isEmpty() && list.isEmpty()) {
            setEmptyListScreen()
        } else {
            list.addAll(newList)
            adapter.setCategory()
        }

        binding.budgetCategoryListFragmentBudgetCategoryListRv.visibility = View.VISIBLE
        binding.budgetCategoryListFragmentEmptyListIv.visibility = View.GONE
        binding.budgetCategoryListFragmentEmptyListHeaderTv.visibility = View.GONE
        binding.budgetCategoryListFragmentEmptyListSubheaderTv.visibility = View.GONE
        binding.budgetCategoryListFragmentPageLoadingPb.visibility = View.GONE
    }


}