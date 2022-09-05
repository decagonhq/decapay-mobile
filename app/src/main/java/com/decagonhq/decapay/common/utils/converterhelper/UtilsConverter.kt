package com.decagonhq.decapay.common.utils.converterhelper

import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

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

    fun formatReceivedTransactionDate(receivedTransactionDate: String): String {
        val dateCollection = receivedTransactionDate.split("-")
        val output = "${dateCollection[2]}/${dateCollection[1]}/${dateCollection[0]}"
        return output
    }

    fun extractMonthFromCurrentDate(currentDate: String): String {
        val dateItems = currentDate.split("/")
        val setDateToFirstItsMonth = "${dateItems[2]}-${dateItems[1]}-01"
        return setDateToFirstItsMonth
    }

    fun formatCalendarSelectedDate(calendarSelectedDate: String): String {
        val selectedDateItem = calendarSelectedDate.split("/")
        val requiredFormat = ""
        return requiredFormat
    }
}
