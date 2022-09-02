package com.decagonhq.decapay.feature.budgetdetails.presentation

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.* // ktlint-disable no-wildcard-imports
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
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
import com.decagonhq.decapay.common.utils.converterhelper.UtilsConverter
import com.decagonhq.decapay.common.utils.converterhelper.getTodaysDate
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentBudgetDetailsBinding
import com.decagonhq.decapay.feature.budgetdetails.adaptor.LineItemAdaptor
import com.decagonhq.decapay.feature.budgetdetails.adaptor.LineItemClicker
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.decagonhq.decapay.presentation.MainActivity
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
    private var calendarSelectedDate: String? = null

    @Inject
    lateinit var budgetDetailsPreference: Preferences
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
        (activity as MainActivity).hideDrawer()
        budgetId = arguments?.getInt(DataConstant.BUDGET_ID)
        if (budgetId != null) {
            budgetDetailsViewModel.getBudgetDetails(budgetId!!)
        }

        binding.budgetDetailsBackIv.setOnClickListener {
            findNavController().navigate(R.id.budgetListFragment)
        }
        // to add budgetlineItems
        binding.budgetDetailsFragmentCreateLineItemFab.setOnClickListener {
            // check budgetId
            val bundle = Bundle()
            if (budgetId != null) {
                bundle.putInt(DataConstant.BUDGET_ID, budgetId!!)
                findNavController().navigate(R.id.createBudgetLineItemBottomSheetFragment, bundle)
            }
        }
        // capture the selected date from the calendar view
        binding.budgetDetailsCalendarCv.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendarSelectedDate = "$dayOfMonth/${month + 1}/$year"
        }

        initObserver()
        lineItemListener()

//        val testList = mutableListOf<LineItem>()
//        list.addAll(testList)
        adapter = LineItemAdaptor(list, this,requireContext())
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

    private fun lineItemListener(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            DataConstant.NEW_LINE_ITEM
        )?.observe(viewLifecycleOwner) {
            it?.let {
                budgetId?.let { it1 -> budgetDetailsViewModel.getBudgetDetails(it1) }
            }
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetDetailsViewModel.budgetDetailsResponse.collect {
                    when (it) {
                        is Resource.Success -> {

                            val budgetDetails = it.data.data

                            binding.budgetDetailsHeaderPeriodTv.text = budgetDetails.budgetPeriod
                            binding.budgetDetailsHeaderDateTv.text =" ${budgetDetails.startDate} - ${budgetDetails.endDate}"

                            binding.budgetDetailsHeaderTitleTv.text = budgetDetails.title
                            binding.budgetDetailsHeaderAmountTv.text =
                                budgetDetails.displayProjectedAmount
                            binding.budgetDetailsTasAmountTv.text =
                                budgetDetails.displayTotalAmountSpentSoFar
                            binding.budgetDetailsPercentageAmountTv.text =
                                budgetDetails.displayPercentageSpentSoFar

                            if(budgetDetails.percentageSpentSoFar>100){
                                binding.budgetDetailsPercentageAmountTv.setTextColor(
                                    AppCompatResources.getColorStateList(requireContext(), R.color.red))
                                binding.budgetDetailsTasAmountTv.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.red))

                            }

                            val formatter = SimpleDateFormat("yyyy.MM.dd, HH:mm")

                            formatter.isLenient = false
                            val startDate = budgetDetails.startDate.replace('-', '.')
                            // the endate to log expense is the current date
                            val receivedEndDate = UtilsConverter.formatCurrentDate(getTodaysDate())
                            val addedOnedDay = UtilsConverter.addOneDayFormat(receivedEndDate)
                            val endDate = budgetDetails.endDate.replace('-', '.')
                            val addedOneDayToEndDate = addedOnedDay.toString().replace('-', '.')



//                            val currentDate = formatter.format(Date())
//
//                            System.out.println(" C DATE is  "+currentDate)

                            val startTime = "$startDate, 00:00"
                            val endTime = "$endDate, 00:00"
                            val addedOneDayToEndDateTime = "$addedOneDayToEndDate, 00:00"
                            val startFormattedDate: Date = formatter.parse(startTime) as Date
                            val endFormattedDate: Date = formatter.parse(endTime) as Date
                            val addedOneDayFormattedDate: Date = formatter.parse(addedOneDayToEndDateTime) as Date
                            val startDateTimeMillis = startFormattedDate.time
                            var endDateTimiMillis = endFormattedDate.time
                            val addedOneDayTimeMillis = addedOneDayFormattedDate.time

                            // save date to sharedPreference
                            budgetDetailsPreference.putBudgetStartDate(startDateTimeMillis)
                            budgetDetailsPreference.putBudgetEndDate(addedOneDayTimeMillis)

                            if(Date().before(endFormattedDate)){
                                endDateTimiMillis = Date().time
                            }

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                budgetDetailsViewModel.budgetDeleteLineItemResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            Snackbar.make(
                                binding.root,
                                "Line Item Deleted",
                                Snackbar.LENGTH_LONG
                            ).show()
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
            budgetDetailsViewModel.deleteLineItem(
                list[position].budgetId,
                list[position].categoryId
            )
            // Log.d("zzz","${list[position].budgetId}, ${list[position].categoryId} ")
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
                        bundle.putSerializable(
                            DataConstant.SELECTED_BUDGET_LINE_ITEM,
                            currentLineItem
                        )
                        findNavController().navigate(
                            R.id.editBudgetLineItemBottomSheetFragment,
                            bundle
                        )
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
        val bundle = Bundle()
        bundle.putSerializable(DataConstant.LOG_EXPENSE_BUDGET_LINE_ITEM_SELECTED, currentLineItem)
        bundle.putString(DataConstant.LOG_EXPENSE_SELECTED_DATE, calendarSelectedDate)
        Log.d(TAG, "selected date on calendar: ${calendarSelectedDate}")
        findNavController().navigate(R.id.logExpenseBottomSheetFragment, bundle)
    }

    override fun onClickItem(currentLineItem: LineItem, position: Int, view: View) {
        val bundle = Bundle()


      //  budgetId?.let { bundle.putInt(DataConstant.BUDGET_ID, it) }
        bundle.putInt(DataConstant.BUDGET_ID, budgetId!!)
        bundle.putString(DataConstant.CATEGORY, currentLineItem.category)
        bundle.putInt(DataConstant.CATEGORY_ID, currentLineItem.categoryId)
        findNavController().navigate(R.id.expensesListFragment, bundle)
    }
}
