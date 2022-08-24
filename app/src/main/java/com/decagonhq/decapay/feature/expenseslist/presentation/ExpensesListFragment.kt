package com.decagonhq.decapay.feature.expenseslist.presentation

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentExpensesListBinding
import com.decagonhq.decapay.feature.expenseslist.adapter.ExpenseClicker
import com.decagonhq.decapay.feature.expenseslist.adapter.ExpenseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpensesListFragment : Fragment(), ExpenseClicker {

    private val expenseListViewModel: ExpenseListViewModel by viewModels()
    private var _binding: FragmentExpensesListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ExpenseListAdapter

    val list = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyList = mutableListOf<Int>(1, 2, 4, 5, 3, 3, 33, 3, 3, 3, 3, 3, 3, 3)
        adapter = ExpenseListAdapter(dummyList, this)
        binding.expenseListFragmentExpensesRv.adapter = adapter
        binding.expenseListFragmentExpensesRv.layoutManager = LinearLayoutManager(requireContext())

        setDataLoaded(dummyList)

        setUpScrollListener()

    }

    override fun onClickItem(currentExpense: Int, position: Int) {

    }

    override fun onClickItemEllipsis(currentExpense: Int, position: Int, view: View) {

        showPopupMenu(position, view, currentExpense)
    }

    private fun showPopupMenu(position: Int, view: View, currentExpense: Int) =
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
                        expenseListViewModel.getNextPage()
                        Log.d("zzz","Called")

                        /**
                        Make network call
                         **/
                    }
                }
            }
        })
    }

    private fun showDialog(position: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.delete_modal_layout)

        val yesBtn = dialog.findViewById(R.id.delete_modal_yes_btn) as Button
        val noBtn = dialog.findViewById(R.id.delete_modal_no_btn) as Button
        yesBtn.setOnClickListener {
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

    private fun setDataLoaded(newList: MutableList<Int>) {

        if (newList.isEmpty() && list.isEmpty()) {
            setEmptyListScreen()
        } else {
            list.addAll(newList)
            adapter.setBudget()
        }

        binding.expenseListFragmentExpensesRv.visibility = View.VISIBLE
        binding.expenseListFragmentEmptyLl.visibility = View.GONE
        binding.expenseListFragmentLoadingPb.visibility = View.GONE
    }


}