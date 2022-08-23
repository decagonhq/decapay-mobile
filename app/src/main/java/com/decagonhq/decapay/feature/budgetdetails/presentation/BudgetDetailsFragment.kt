package com.decagonhq.decapay.feature.budgetdetails.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentBudgetDetailsBinding
import com.decagonhq.decapay.feature.budgetdetails.adaptor.LineItemAdaptor
import com.decagonhq.decapay.feature.budgetdetails.adaptor.LineItemClicker
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BudgetDetailsFragment : Fragment(), LineItemClicker {

    private val TAG = "BUDGETDETAILSFRAG"
    private var _binding: FragmentBudgetDetailsBinding? = null
    val binding get() = _binding!!
    private var budgetId: Int? = null
    private var detailsBudgetId: Content? = null
    private var list = mutableListOf<LineItem>()
    private lateinit var adapter: LineItemAdaptor

    @Inject
    lateinit var preference: Preferences
    private val budgetDetailsViewModel: BudgetDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this handles the backPress; all navigation moves to list budget
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (shouldInterceptBackpress()) {
                        findNavController().navigate(
                            R.id.budgetListFragment
                        )
                    } else {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            }
        )
    }

    fun shouldInterceptBackpress() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        budgetId = arguments?.getInt(DataConstant.BUDGET_ID)
        if (budgetId != null) {
            budgetDetailsViewModel.getBudgetDetails(budgetId!!)
        }

        // to add budgetlineItems
        binding.budgetDetailsFragmentCreateLineItemFab.setOnClickListener {
            // check budgetId and detailsBudgetId for BudgetID
            val bundle = Bundle()
            if (budgetId != null) {
                bundle.putInt(DataConstant.BUDGET_ID, budgetId!!)
                findNavController().navigate(R.id.createBudgetLineItemBottomSheetFragment, bundle)
            }
        }

        initObserver()

//        val testList = mutableListOf<LineItem>()
//        list.addAll(testList)
        adapter = LineItemAdaptor(list, this)
        binding.budgetDetailsLineItemsRv.adapter = adapter
        binding.budgetDetailsLineItemsRv.layoutManager =
            LinearLayoutManager(requireContext())
        // setDataLoaded(list);
    }

    private fun setDataLoaded(list: MutableList<LineItem>) {
        if (list.isEmpty()) {
            binding.budgetDetailsEmptyLineItemsLl.visibility = View.VISIBLE
        } else {
            binding.budgetDetailsEmptyLineItemsLl.visibility = View.GONE
            this.list = list
            adapter.list = list
            adapter.setLineItems()
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetDetailsViewModel.budgetDetailsResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            Snackbar.make(
                                binding.root,
                                it.data.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                            val budgetDetails = it.data.data

                            binding.budgetDetailsHeaderTitleTv.text = budgetDetails.title
                            binding.budgetDetailsHeaderAmountTv.text =
                                budgetDetails.displayProjectedAmount
                            binding.budgetDetailsTasAmountTv.text =
                                budgetDetails.displayTotalAmountSpentSoFar
                            binding.budgetDetailsPercentageAmountTv.text =
                                budgetDetails.displayPercentageSpentSoFar

                            val formatter = SimpleDateFormat("yyyy.MM.dd, HH:mm")

                            formatter.isLenient = false
                            val startDate = budgetDetails.startDate.replace('-', '.')
                            val endDate = budgetDetails.endDate.replace('-', '.')
                            val startTime = "$startDate, 00:00"
                            val endTime = "$endDate, 00:00"
                            val startFormattedDate: Date = formatter.parse(startTime) as Date
                            val endFormattedDate: Date = formatter.parse(endTime) as Date
                            val startDateTimeMillis = startFormattedDate.time
                            val endDateTimiMillis = endFormattedDate.time

                            binding.budgetDetailsCalendarCv.maxDate = endDateTimiMillis
                            binding.budgetDetailsCalendarCv.minDate = startDateTimeMillis

                            setDataLoaded(it.data.data.lineItems.toMutableList())
                        }
                        is Resource.Error -> {
                            Snackbar.make(
                                binding.root,
                                it.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun showDeleteDialog(position: Int) {
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

    private fun showPopupMenu(position: Int, view: View, currentLineItem: LineItem) =
        PopupMenu(view.context, view, Gravity.RIGHT).run {
            menuInflater.inflate(R.menu.category_item_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {
                        val bundle = Bundle()
                        bundle.putSerializable(DataConstant.SELECTED_BUDGET_LINE_ITEM, currentLineItem)
                        findNavController().navigate(R.id.editBudgetLineItemBottomSheetFragment, bundle)
                    }
                    "Delete" -> {
                        showDeleteDialog(position)
                    }
                }
                true
            }
            show()
        }

    override fun onClickItemEllipsis(currentLineItem: LineItem, position: Int, view: View) {
        showPopupMenu(position, view, currentLineItem)
    }

    override fun onClickItemLog(currentLineItem: LineItem, position: Int, view: View) {
    }
}
