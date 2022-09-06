package com.decagonhq.decapay.feature.listbudget.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentBudgetListBinding
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetClicker
import com.decagonhq.decapay.feature.listbudget.adapter.BudgetListAdapter
import com.decagonhq.decapay.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BudgetListFragment : Fragment(), BudgetClicker {

    @Inject
    lateinit var preference: Preferences
    private val budgetListViewModel: BudgetListViewModel by viewModels()
    private var _binding: FragmentBudgetListBinding? = null
    private lateinit var adapter: BudgetListAdapter
    private val binding get() = _binding!!
    private val TAG = "BUDGETLISTFRAGMENT"

    private val list = mutableListOf<Content>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBudgetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // outState.pu("LIST",list )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).revealDrawer()

//        list.clear()
//        budgetListViewModel.getBudgetList("current")

        adapter = BudgetListAdapter(list, this, requireContext())
        binding.budgetListFragmentBudgetListRv.adapter = adapter
        binding.budgetListFragmentBudgetListRv.layoutManager = LinearLayoutManager(requireContext())

        binding.budgetListFragmentCreateBudgetFab.setOnClickListener {
            findNavController().navigate(R.id.createBudgetFragment)
        }

        setUpScrollListener()

        setUpFlowListener()

        setUpSpinner()
    }

    private fun setUpSpinner() {
        val states = resources.getStringArray(R.array.States)
        val spinnerAdapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, states)
        binding.budgetListFragmentFilterSpinner.adapter = spinnerAdapter
        binding.budgetListFragmentFilterSpinner.setSelection(2)
        binding.budgetListFragmentFilterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (budgetListViewModel.budgetTypePosition != position) {
                        Log.d(
                            "spinner",
                            "call-me, budgetTypePosition: ${budgetListViewModel.budgetTypePosition}, position: $position"
                        )
                        budgetListViewModel.budgetTypePosition = position
                        budgetListViewModel.isLastPage = true
                        list.clear()
                        adapter.clearList()
                        when (position) {
                            0 -> budgetListViewModel.getBudgetList("")
                            1 -> budgetListViewModel.getBudgetList("past")
                            2 -> budgetListViewModel.getBudgetList("current")
                            3 -> budgetListViewModel.getBudgetList("upcoming")
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun setUpFlowListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                list.clear()
                budgetListViewModel.page = 1
                budgetListViewModel.budgetListResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            setIsLoadingScreen()
                        }

                        is Resource.Success -> {
                            Log.d("zzz", "This is emitted ${it.data.size}")
                            setDataLoaded(it.data as MutableList<Content>)
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
        showPopupMenu(position, view, currentBudget)
    }

    private fun goToBudgetDetails(currentBudget: Content) {
        val bundle = Bundle()
        bundle.putInt(DataConstant.BUDGET_ID, currentBudget.id)
        findNavController().navigate(R.id.budgetDetailsFragment, bundle)
    }

    private fun showPopupMenu(position: Int, view: View, currentBudget: Content) =
        PopupMenu(view.context, view).run {
            menuInflater.inflate(R.menu.budget_item_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {
                        val bundle = Bundle()
                        bundle.putInt(DataConstant.BUDGET_ID, currentBudget.id)
                        findNavController().navigate(R.id.editBudgetFragment, bundle)
                    }
                    "View details" -> {
                        val bundle = Bundle()
                        bundle.putSerializable(DataConstant.BUDGET_ID, adapter.list[position].id)
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
                            budgetListViewModel.getNextPage()

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
        if (newList.isEmpty()) {
            setEmptyListScreen()
        } else {
            list.clear()
            list.addAll(newList)
            adapter.setBudget()

            binding.budgetListFragmentBudgetListRv.visibility = View.VISIBLE
            binding.budgetListFragmentEmptyLl.visibility = View.GONE
            binding.budgetListFragmentPageLoadingPb.visibility = View.GONE
        }
    }
}
