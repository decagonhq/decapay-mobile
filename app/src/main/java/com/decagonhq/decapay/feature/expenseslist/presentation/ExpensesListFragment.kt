package com.decagonhq.decapay.feature.expenseslist.presentation

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentExpensesListBinding
import com.decagonhq.decapay.feature.expenseslist.adapter.ExpenseClicker
import com.decagonhq.decapay.feature.expenseslist.adapter.ExpenseListAdapter
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExpensesListFragment : Fragment(), ExpenseClicker {

    private val expenseListViewModel: ExpenseListViewModel by viewModels()
    private var _binding: FragmentExpensesListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ExpenseListAdapter

    @Inject
    lateinit var expenseListPreferences: Preferences

    var budgetId: Int? = null
    var categoryId: Int? = null

    val list = mutableListOf<ExpenseContent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        budgetId = arguments?.getInt(DataConstant.BUDGET_ID)
        categoryId = arguments?.getInt(DataConstant.CATEGORY_ID)
        val title = arguments?.getString(DataConstant.CATEGORY)

        binding.expenseListFragmentToolbarTitle.text = "$title Expenses"

        // save the expense category title to sharedPreference
        expenseListPreferences.putExpenseCategoryTitle(title.toString())

        if (budgetId != null && categoryId != null) {
            expenseListViewModel.getBudgetList(budgetId!!, categoryId!!)
        }

        adapter = ExpenseListAdapter(list, this)
        binding.expenseListFragmentExpensesRv.adapter = adapter
        binding.expenseListFragmentExpensesRv.layoutManager =
            LinearLayoutManager(requireContext())

        setUpScrollListener()
        setUpFlowListener()
        setUpDeleteFlowListener()
    }

    override fun onClickItem(currentExpense: ExpenseContent, position: Int) {
    }

    override fun onClickItemEllipsis(currentExpense: ExpenseContent, position: Int, view: View) {

        showPopupMenu(position, view, currentExpense)
    }

    private fun showPopupMenu(position: Int, view: View, currentExpense: ExpenseContent) =
        PopupMenu(view.context, view).run {
            menuInflater.inflate(R.menu.category_item_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {
                        val bundle = Bundle()
                        bundle.putSerializable(DataConstant.EXPENSE_DATA, currentExpense)
                        findNavController().navigate(R.id.editLogExpenseBottomSheetFragment, bundle)
                    }
                    "Delete" -> {
                        showDialog(position)
                    }
                }
                true
            }
            show()
        }

    private fun setUpScrollListener() {
        binding.expenseListFragmentExpensesRv.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    if (dy > 0) {
                        val visibleItemCount = recyclerView.layoutManager!!.childCount
                        val totalItemCount = recyclerView.layoutManager!!.itemCount
                        val pastVisibleItems =
                            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        if (visibleItemCount + pastVisibleItems >= totalItemCount - 2) {
                            expenseListViewModel.getNextPage(budgetId!!, categoryId!!)
                            Log.d("zzz", "Called")

                            /**
                             Make network call
                             **/
                        }
                    }
                }
            })
    }

    private fun setUpFlowListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                expenseListViewModel.expenseListResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            setIsLoadingScreen()
                        }

                        is Resource.Success -> {
                            setDataLoaded(it.data.data.content as MutableList<ExpenseContent>)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setUpDeleteFlowListener() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                expenseListViewModel.deleteResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            setIsLoadingScreen()
                        }

                        is Resource.Success -> {
                            Snackbar.make(
                                binding.root,
                                "Expense deleted successfully",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Snackbar.make(
                                binding.root,
                                "${it.messages}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun showDialog(position: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.delete_modal_layout)

        val yesBtn = dialog.findViewById(R.id.delete_modal_yes_btn) as Button
        val noBtn = dialog.findViewById(R.id.delete_modal_no_btn) as Button
        yesBtn.setOnClickListener {
            expenseListViewModel.deleteExpense(adapter.list[position].id)

            adapter.deleteItemAtIndex(position)

            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun setIsLoadingScreen() {
        binding.expenseListFragmentEmptyLl.visibility = View.GONE
        binding.expenseListFragmentExpensesRv.visibility = View.GONE
        binding.expenseListFragmentLoadingPb.visibility = View.VISIBLE
    }

    private fun setEmptyListScreen() {
        binding.expenseListFragmentLoadingPb.visibility = View.GONE
        binding.expenseListFragmentExpensesRv.visibility = View.GONE
        binding.expenseListFragmentEmptyLl.visibility = View.VISIBLE
    }

    private fun setDataLoaded(newList: MutableList<ExpenseContent>) {

        if (newList.isEmpty() && list.isEmpty()) {
            setEmptyListScreen()
        } else {
            list.addAll(newList)
            adapter.setBudget()

            binding.expenseListFragmentExpensesRv.visibility = View.VISIBLE
            binding.expenseListFragmentEmptyLl.visibility = View.GONE
            binding.expenseListFragmentLoadingPb.visibility = View.GONE
        }
    }
}
