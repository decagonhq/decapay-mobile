package com.decagonhq.decapay.feature.createbudget.presentation.bottomsheet

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.decagonhq.decapay.common.utils.bottomsheetcommunicationonclickinterface.BottomSheetOnclickInterface
import com.decagonhq.decapay.databinding.FragmentOptionModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class OptionModalBottomSheetFragment(private val listener: BottomSheetOnclickInterface) : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "OPTIONMENUBOTSHEET"
    private var _binding: FragmentOptionModalBottomSheetBinding? = null
    private val binding: FragmentOptionModalBottomSheetBinding get() = _binding!!
    lateinit var inputPeriod: String
    private var onClickInterface: BottomSheetOnclickInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOptionModalBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOptionModalBottomSheetBinding.bind(view)

        // display the selected period

        /** on click of the annual period */

        binding.optionModalBottomSheetFragmentOptionsAnnualTv.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "I am clicked",
                Toast.LENGTH_LONG
            ).show()

            /*
            val calenda = Calendar.getInstance()
            val myYear = calenda.get(Calendar.YEAR)
            val myMonth = calenda.get(Calendar.MONTH)
            val myDay = calenda.get(Calendar.DAY_OF_MONTH)
            val id = Locale("en", "NG")
            val simpleDateFormat = SimpleDateFormat("dd/mm/yy", id)
            val datePickeDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.d(TAG, "$year")
                    val action = OptionModalBottomSheetFragmentDirections.actionOptionModalBottomSheetFragmentToCreateBudgetFragment(
                        year.toString()
                    )
                    findNavController().navigate(action)
                },
                myYear, myMonth, myDay
            )
            datePickeDialog.show()
            */
        }
        /** on click of the monthly period */
//        binding.optionModalBottomSheetFragmentOptionsMonthlyTv.setOnClickListener { }

        /** on click of the weekly period */
        binding.optionModalBottomSheetFragmentOptionsWeeklyTv.setOnClickListener {
            showDateRange()
        }

        /** on click of the daily period */
        binding.optionModalBottomSheetFragmentOptionsDailyTv.setOnClickListener {
            val calenda = Calendar.getInstance()
            val yearNow = calenda.get(Calendar.YEAR)
            val monthNow = calenda.get(Calendar.MONTH)
            val dayNow = calenda.get(Calendar.DAY_OF_MONTH)
            val id = Locale("en", "ID")
            val simpleDateFormat = SimpleDateFormat("dd/mm/yyyy", id)
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calenda.set(year, month, dayOfMonth)
                    inputPeriod = simpleDateFormat.format(calenda.time)
                    /** Pass the selected date to the listener here */
                    listener.passDataFromOptionBottomSheetToCreateBudgetFragment(inputPeriod)
                },
                yearNow, monthNow, dayNow
            )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
//            findNavController().navigate(R.id.createBudgetFragment)
        }

        /** on click of the custom period */
//        binding.optionModalBottomSheetFragmentOptionsCustomTv.setOnClickListener {  }

        // close the bottom sheet
        binding.optionModalBottomSheetFragmentCloseIconIv.setOnClickListener {
            dismiss()
        }
    }

    private fun showDateRange() {
        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()
        dateRangePicker.show(
            parentFragmentManager,
            "date_range_picker"
        )
        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
            val startDate = dateSelected.first
            val endDate = dateSelected.second
            Log.d(TAG, "this is how the date object looks like: ${startDate}")

            if (startDate != null && endDate != null) {
                inputPeriod = "${convertLongToTime(startDate)}, ${convertLongToTime(endDate)}"
                listener.passDataFromOptionBottomSheetToCreateBudgetFragment(inputPeriod)
                Log.d(TAG, "here is the start and end date: $inputPeriod")
            }
        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )
        return format.format(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
