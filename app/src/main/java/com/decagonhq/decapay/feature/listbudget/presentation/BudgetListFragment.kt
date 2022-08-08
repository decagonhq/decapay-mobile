package com.decagonhq.decapay.feature.listbudget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentBudgetListBinding
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetClicker
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetListAdapter
import com.decagonhq.decapay.common.data.model.Content
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BudgetListFragment : Fragment(), BudgetClicker {

    @Inject
    lateinit var preference: Preferences
    private val budgetListViewModel: BudgetListViewModel by viewModels()
    private var _binding: FragmentBudgetListBinding? = null
    private lateinit var adapter: BudgetListAdapter
    private val binding get() = _binding!!

    private val list = mutableListOf<Content>()

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

        // val list = mutableListOf<Int>()

        budgetListViewModel.getBudgetList(preference.getToken())
        adapter = BudgetListAdapter(list, this)
        binding.budgetListFragmentBudgetListRv.adapter = adapter
        binding.budgetListFragmentBudgetListRv.layoutManager = LinearLayoutManager(requireContext())



        binding.budgetListFragmentCreateBudgetFab.setOnClickListener {

        }

        setUpScrollListener()

        setUpFlowListener()
    }

    private fun setUpFlowListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetListViewModel.budgetListResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            setIsLoadingScreen()
                        }

                        is Resource.Success -> {
                            setDataLoaded(it.data.data.content as MutableList<Content>)
                        }
                        else -> {}
                    }


                }
            }
        }
    }

    override fun onClickItem(currentBudget: Content, position: Int) {
        goToBudgetDetails(currentBudget)

    }




    override fun onClickItemEllipsis(currentBudget: Content, position: Int, view: View) {
        //println("Clicked on an item elipsis")
        showPopupMenu(position, view)
    }


    private fun goToBudgetDetails(currentBudget: Content){
        val bundle = Bundle()
        bundle.putSerializable("BUDGET_ITEM", currentBudget)
        findNavController().navigate(R.id.budgetDetailsFragment, bundle)
    }

    private fun showPopupMenu(position: Int, view: View) =
        PopupMenu(view.context, view).run {
            menuInflater.inflate(R.menu.budget_item_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {

                    }
                    "View details" -> {
                        val bundle = Bundle()
                        bundle.putSerializable("BUDGET_ITEM", adapter.list[position])
                        findNavController().navigate(R.id.budgetDetailsFragment, bundle)
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

                if (dy > 0) {
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisibleItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 2) {
                        budgetListViewModel.getNextPage(preference.getToken())

                        /**
                        Make network call
                         **/
                    }


                }

            }
        })
    }


    private fun setIsLoadingScreen() {
        binding.budgetListFragmentBudgetListRv.visibility = View.GONE
        binding.budgetListFragmentEmptyLl.visibility = View.GONE
        binding.budgetListFragmentPageLoadingPb.visibility = View.VISIBLE

    }

    private fun setEmptyListScreen() {
        binding.budgetListFragmentBudgetListRv.visibility = View.GONE
        binding.budgetListFragmentEmptyLl.visibility = View.VISIBLE
        binding.budgetListFragmentPageLoadingPb.visibility = View.GONE
    }

    private fun setDataLoaded(newList: MutableList<Content>) {

        if (newList.isEmpty() && list.isEmpty()) {
            setEmptyListScreen()
        } else {
            list.addAll(newList)
            adapter.setBudget()
        }

        binding.budgetListFragmentBudgetListRv.visibility = View.VISIBLE
        binding.budgetListFragmentEmptyLl.visibility = View.GONE
        binding.budgetListFragmentPageLoadingPb.visibility = View.GONE
    }

}