package com.decagonhq.decapay.common.utils.converterhelper

import java.time.LocalDate
import java.time.format.DateTimeParseException

object UtilsConverter {

    fun addOneDayFormat(receivedDate: String): String {
        var result = ""
        try {
            result = LocalDate.parse(receivedDate).plusDays(1).toString()
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
        }
        return result
    }

    fun formatCurrentDate(receivedDate: String): String {
        val arrayDateItems = receivedDate.split("/")
        val result = "${arrayDateItems[2]}-${arrayDateItems[1]}-${arrayDateItems[0]}"
        return result
    }
}
