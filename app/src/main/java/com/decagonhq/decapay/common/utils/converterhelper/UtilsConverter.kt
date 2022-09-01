package com.decagonhq.decapay.common.utils.converterhelper

import java.text.NumberFormat
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

    fun currencyFormatter(number: String): String {
        val countryCode = "NG"
        val language = "en"
        val locale = Locale(language, countryCode)
        val currency = Currency.getInstance(locale)
        val formStringAmount = number.replace("[${currency.symbol},.]".toRegex(), "")
        val parsed = formStringAmount.toDouble()
        val formatted = NumberFormat.getCurrencyInstance(locale).format(parsed/100)
        return formatted
    }
}
