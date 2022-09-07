package com.decagonhq.decapay.common.utils.converterhelper

import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.* // ktlint-disable no-wildcard-imports
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

fun buildDatePickerWithConstraint(constraint: CalendarConstraints): MaterialDatePicker<Long> {
    return MaterialDatePicker.Builder.datePicker().setCalendarConstraints(constraint).build()
}

fun buildDateRangeConstraint(startDate: Long, endDate: Long): CalendarConstraints {
    val constraintsBuilderRange = CalendarConstraints.Builder()
    val dateConstraintsFromDate = DateValidatorPointForward.from(startDate)
    val dateConstraintsToDate = DateValidatorPointBackward.before(endDate)
    val listValidators = ArrayList<CalendarConstraints.DateValidator>()
    listValidators.add(dateConstraintsFromDate)
    listValidators.add(dateConstraintsToDate)
    val validators = CompositeDateValidator.allOf(listValidators)
    constraintsBuilderRange.setValidator(validators)
    return constraintsBuilderRange.build()
}
