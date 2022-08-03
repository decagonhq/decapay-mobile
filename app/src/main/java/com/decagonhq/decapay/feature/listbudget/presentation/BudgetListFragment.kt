package com.decagonhq.decapay.feature.listbudget.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.FragmentBudgetListBinding
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetClicker
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetListAdapter


class BudgetListFragment : Fragment(), BudgetClicker {

    private var _binding: FragmentBudgetListBinding? = null
    private lateinit var adapter: BudgetListAdapter
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.budgetListFragmentBudgetListRv.visibility = View.GONE
        val list = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 101, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        adapter = BudgetListAdapter(list, this)
        binding.budgetListFragmentBudgetListRv.adapter = adapter
        binding.budgetListFragmentBudgetListRv.layoutManager = LinearLayoutManager(requireContext())



        binding.budgetListFragmentCreateBudgetFab.setOnClickListener {

        }

        setUpScrollListener();
    }

    override fun onClickItem(currentBudget: Int, position: Int) {
        // println("Clicked on an item on list")

        // findNavController().navigate(R.id.signUpFragment)

    }

    override fun onClickItemElipsis(currentBudget: Int, position: Int, view: View) {
        //println("Clicked on an item elipsis")
        showPopupMenu(currentBudget, position, view)
    }

    private fun showPopupMenu(currentBudget: Int, position: Int, view: View) =
        PopupMenu(view.context, view).run {
            menuInflater.inflate(R.menu.budget_item_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {

                    }
                    "View details" -> {

                    }
                    "Delete" -> {
                        adapter.deleteItemAtIndex(position)
                    }
                }
                true
            }
            show()
        }


    private fun setUpScrollListener() {
        binding.budgetListFragmentBudgetListRv.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
              
                if(dy>0){
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val   pastVisibleItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if(visibleItemCount + pastVisibleItems >= totalItemCount-3){


                        /**
                         Make network call
                         **/
                    }



                }

            }
        })
    }

}