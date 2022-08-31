package com.decagonhq.decapay.common.utils.converterhelper

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.*
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    )
    return format.format(date)
}

fun Fragment.getTodaysDate(): String {
    val calenda = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = dateFormat.format(calenda.time)
    return date
}

fun Fragment.showDateRange(startDate: Long, endDate: Long, view: View, viewId: Int) {
    val builderRange = MaterialDatePicker.Builder.datePicker()
    val constraintsBuilderRange = CalendarConstraints.Builder()
    val dateValidatorStartDate = DateValidatorPointForward.from(startDate)
    val dateValidatorEndDate = DateValidatorPointBackward.before(endDate)
    val listValidators = ArrayList<CalendarConstraints.DateValidator>()
    listValidators.add(dateValidatorStartDate)
    listValidators.add(dateValidatorEndDate)
    val validators = CompositeDateValidator.allOf(listValidators)
    constraintsBuilderRange.setValidator(validators)
    builderRange.setCalendarConstraints(constraintsBuilderRange.build())
    val pickRange = builderRange.build()
    pickRange.show(parentFragmentManager, pickRange.toString())
    pickRange.addOnPositiveButtonClickListener { selectedDate ->
        view.findViewById<TextView>(viewId).text = "${convertLongToTime(selectedDate)}"
    }
}
