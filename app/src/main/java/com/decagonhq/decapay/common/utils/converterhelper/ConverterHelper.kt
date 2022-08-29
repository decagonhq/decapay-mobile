package com.decagonhq.decapay.common.utils.converterhelper

import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeParseException
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

fun Fragment.addOneDayToEndDate(date: String): String {
    val arrayDateItems = date.split("-")
    val firstItem = arrayDateItems[0].toInt()
    var secondItem = arrayDateItems[1].toInt()
    var thirdItem = arrayDateItems[2].toInt()
    // months that are only 30days, september(9), april(4), june(6), november(11)
    val april = 4
    val june = 6
    val september = 9
    val november = 11
    val february = 2
    val january = 1
    val march = 3
    val may = 5
    val july = 7
    val august = 8
    val october = 10
    val december = 12
    var result = ""
    var concatenateThirdItem = ""

    if ((secondItem == april || secondItem == june || secondItem == september || secondItem == november) && (thirdItem == 30)) {
        // if the day is 30, set the month to next month by increasing it by 1
        secondItem++
        thirdItem = 1
        // convert to String
        firstItem.toString()
        secondItem.toString()
        thirdItem.toString()
        if ("$thirdItem".length <= 1) {
            concatenateThirdItem = "0$thirdItem"
        } else {
            concatenateThirdItem = "$thirdItem"
        }

        if ("$secondItem".length <= 1) {
            result = "$firstItem/0$secondItem/$concatenateThirdItem"
        } else {
            result = "$firstItem/$secondItem/$concatenateThirdItem"
        }
    } else if (secondItem == february && (thirdItem == 28)) {
        secondItem++
        thirdItem = 1
        if ("$thirdItem".length <= 1) {
            concatenateThirdItem = "0$thirdItem"
        } else {
            concatenateThirdItem = "$thirdItem"
        }

        if ("$secondItem".length <= 1) {
            result = "$firstItem/0$secondItem/$concatenateThirdItem"
        } else {
            result = "$firstItem/$secondItem/$concatenateThirdItem"
        }
    } else if (secondItem == february && (thirdItem == 29)) {
        secondItem++
        thirdItem = 1
        if ("$thirdItem".length <= 1) {
            concatenateThirdItem = "0$thirdItem"
        } else {
            concatenateThirdItem = "$thirdItem"
        }

        if ("$secondItem".length <= 1) {
            result = "$firstItem/0$secondItem/$concatenateThirdItem"
        } else {
            result = "$firstItem/$secondItem/$concatenateThirdItem"
        }
    } else if (secondItem == january || secondItem == march || secondItem == may || secondItem == july || secondItem == august || secondItem == october || secondItem == december && (thirdItem == 31)) {
        secondItem++
        thirdItem = 1
        if ("$thirdItem".length <= 1) {
            concatenateThirdItem = "0$thirdItem"
        } else {
            concatenateThirdItem = "$thirdItem"
        }

        if ("$secondItem".length <= 1) {
            result = "$firstItem/0$secondItem/$concatenateThirdItem"
        } else {
            result = "$firstItem/$secondItem/$concatenateThirdItem"
        }
    } else {
        thirdItem++
        if ("$thirdItem".length <= 1) {
            concatenateThirdItem = "0$thirdItem"
        } else {
            concatenateThirdItem = "$thirdItem"
        }

        if ("$secondItem".length <= 1) {
            result = "$firstItem/0$secondItem/$concatenateThirdItem"
        } else {
            result = "$firstItem/$secondItem/$concatenateThirdItem"
        }
    }
    return result
}

fun Fragment.addOneDay(receivedDate: String): String {
    var result = ""
    try {
        result = LocalDate.parse(receivedDate).plusDays(1).toString()
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
    }
    return result
}
