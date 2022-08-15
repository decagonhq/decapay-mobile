package com.decagonhq.decapay.common.utils.converterhelper

import androidx.fragment.app.Fragment
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