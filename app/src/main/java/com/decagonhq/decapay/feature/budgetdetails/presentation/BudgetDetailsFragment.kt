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
import com.decagonhq.decapay.common.utils.uihelpers.showInfoMsgSessionExpired
import com.decagonhq.decapay.databinding.FragmentBudgetDetailsBinding
import com.decagonhq.decapay.feature.budgetdetails.adaptor.LineItemAdaptor
import com.decagonhq.decapay.feature.budgetdetails.adaptor.LineItemClicker
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.bundle.LogExpenseData
import com.decagonhq.decapay.presentation.BaseActivity
import com.decagonhq.decapay.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class BudgetDetailsFragment : Fragment(), LineItemClicker {

    private val TAG = "BUDGETDETAILSFRAG"
    private var _binding: FragmentBudgetDetailsBinding? = null
    val binding get() = _binding!!
    private var budgetId: Int? = null
    private var list = mutableListOf<LineItem>()
    private lateinit var adapter: LineItemAdaptor
    private var calendarSelectedDate: String? = null
    private lateinit var logExpenseData: LogExpenseData
    private var budgetPeriod: String? = null

    @Inject
    lateinit var budgetDetailsPreference: Preferences
    private val budgetDetailsViewModel: BudgetDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logExpenseData = LogExpenseData(null, null, null, null, null, null)
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
        (activity as BaseActivity).hideDrawer()
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
                bundle.putString(DataConstant.BUDGET_PERIOD, budgetPeriod)
                findNavController().navigate(R.id.createBudgetLineItemBottomSheetFragment, bundle)
            }
        }
        // capture the selected date from the calendar view
        binding.budgetDetailsCalendarCv.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDayOfMonth = if ("$dayOfMonth".length == 1) "0$dayOfMonth" else "$dayOfMonth"
            val selectedMonth = if ("${month + 1}".length == 1) "0${month + 1}" else "${month + 1}"
            calendarSelectedDate = "$selectedDayOfMonth/$selectedMonth/$year"
        }

        initObserver()
        lineItemListener()

//        val testList = mutableListOf<LineItem>()
//        list.addAll(testList)
        adapter = LineItemAdaptor(list, this, requireContext())
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

    private fun lineItemListener() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            DataConstant.UPDATE_UI
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
                            // capture budget period to save to bundle
                            budgetPeriod = budgetDetails.budgetPeriod

                            binding.budgetDetailsHeaderPeriodTv.text = budgetDetails.budgetPeriod
                            binding.budgetDetailsHeaderDateTv.text = " ${budgetDetails.displayStartDate} - ${budgetDetails.displayEndDate}"

                            binding.budgetDetailsHeaderTitleTv.text = budgetDetails.title
                            binding.budgetDetailsHeaderAmountTv.text =
                                budgetDetails.displayProjectedAmount
                            binding.budgetDetailsTasAmountTv.text =
                                budgetDetails.displayTotalAmountSpentSoFar
                            binding.budgetDetailsPercentageAmountTv.text =
                                budgetDetails.displayPercentageSpentSoFar

                            if (budgetDetails.percentageSpentSoFar > DataConstant.MAX_PERCENT) {
                                binding.budgetDetailsPercentageAmountTv.setTextColor(
                                    AppCompatResources.getColorStateList(requireContext(), R.color.red)
                                )
                                binding.budgetDetailsTasAmountTv.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.red))
                            }

                            val formatter = SimpleDateFormat(DataConstant.DATE_FORMAT)

                            formatter.isLenient = false
                            val startDate = budgetDetails.startDate.replace('-', '.')
                            // the endate to log expense is the current date
                            val currentDate = UtilsConverter.formatCurrentDate(getTodaysDate())
                            var validEndate: String? = null
                            if (currentDate > budgetDetails.endDate) {
                                validEndate = budgetDetails.endDate
                            } else {
                                validEndate = currentDate
                            }
                            val extractedMonthFromCurrentDateToSetStartDateForFutureBudget = UtilsConverter.extractMonthFromCurrentDate(getTodaysDate())
                            val addedOneDayToCurrentDate = UtilsConverter.addOneDayFormat(validEndate)
                            val addOneDayToEndDateFromRemote = UtilsConverter.addOneDayFormat(budgetDetails.endDate)
                            val endDate = budgetDetails.endDate.replace('-', '.')
                            val addedOneDayTodaysDate = addedOneDayToCurrentDate.toString().replace('-', '.')
                            val addedOneDayToEndDateFromRemoteDate = addOneDayToEndDateFromRemote.replace('-', '.')
                            val extractedMonthFromCurrentDateToSetStartDateForFutureBudgetDate = extractedMonthFromCurrentDateToSetStartDateForFutureBudget.replace('-', '.')

                            val startTime = "$startDate, 00:00"
                            val endTime = "$endDate, 00:00"
                            val addedOneDayTodaysDateTime = "$addedOneDayTodaysDate, 00:00"
                            val addedOneDayToEndDateFromRemoteTime = "$addedOneDayToEndDateFromRemoteDate, 00:00"
                            val extractedMonthFromCurrentDateToSetStartDateForFutureBudgetDateTime = "$extractedMonthFromCurrentDateToSetStartDateForFutureBudgetDate, 00:00"
                            val startFormattedDate: Date = formatter.parse(startTime) as Date
                            val endFormattedDate: Date = formatter.parse(endTime) as Date
                            val addedOneDayToTodaysFormattedDate: Date = formatter.parse(addedOneDayTodaysDateTime) as Date
                            val addedOneDayToEndDateFromRemoteFormattedDate = formatter.parse(addedOneDayToEndDateFromRemoteTime) as Date
                            val extractedMonthFromCurrentDateToSetStartDateForFutureBudgetFormattedDate: Date = formatter.parse(extractedMonthFromCurrentDateToSetStartDateForFutureBudgetDateTime) as Date
                            val startDateTimeMillis = startFormattedDate.time
                            var endDateTimiMillis = endFormattedDate.time
                            val addedOneDayToTodaysTimeMillis = addedOneDayToTodaysFormattedDate.time
                            val addedOneDayToEndDateFromRemoteTimeMillis = addedOneDayToEndDateFromRemoteFormattedDate.time
                            val extractedMonthFromCurrentDateToSetStartDateForFutureBudgetTimeMillis = extractedMonthFromCurrentDateToSetStartDateForFutureBudgetFormattedDate.time
                            logExpenseData.startDateCaptured = startDateTimeMillis
                            logExpenseData.endDateCaptured = addedOneDayToTodaysTimeMillis

                            if (Date().before(endFormattedDate)) {
                                endDateTimiMillis = Date().time
                            }

                            binding.budgetDetailsCalendarCv.maxDate = endDateTimiMillis
                            binding.budgetDetailsCalendarCv.minDate = startDateTimeMillis

                            setDataLoaded(it.data.data.lineItems.toMutableList())
                        }
                        is Resource.Error -> {
                            // check when it is UNAUTHORIZED
                            when(it.message) {
                                "UNAUTHORIZED" -> {
                                    // navigate to login
                                    // show a dialog
                                    findNavController().navigate(R.id.loginFragment)
                                    showInfoMsgSessionExpired()
                                }
                                else -> {
                                    Snackbar.make(
                                        binding.root,
                                        "${it.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
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
            list[position].budgetId?.let { currentBudgetId ->
                list[position].categoryId?.let { currentCategoryId ->
                    budgetDetailsViewModel.deleteLineItem(
                        currentBudgetId,
                        currentCategoryId
                    )
                }
            }
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
        val bundle = buildBundle(currentLineItem)
        findNavController().navigate(R.id.logExpenseBottomSheetFragment, bundle)
        Log.d("inspect", "inside loExpenseData: $logExpenseData")
    }

    /**
     * TODO remember to add commnets to this method
     */
    override fun onClickItem(currentLineItem: LineItem, position: Int, view: View) {
        val bundle = buildBundle(currentLineItem)
        findNavController().navigate(R.id.expensesListFragment, bundle)
    }

    private fun populateLogExpenseData(currentLineItem: LineItem) {
        logExpenseData.budgetId = currentLineItem.budgetId
        logExpenseData.categoryId = currentLineItem.categoryId
        logExpenseData.category = currentLineItem.category
        logExpenseData.calendarSelectedDate = calendarSelectedDate
    }

    private fun buildBundle(currentLineItem: LineItem): Bundle {
        val bundle = Bundle()
        populateLogExpenseData(currentLineItem)
        bundle.putSerializable(DataConstant.LOG_EXPENSE_DATA, logExpenseData)
        return bundle
    }
}
