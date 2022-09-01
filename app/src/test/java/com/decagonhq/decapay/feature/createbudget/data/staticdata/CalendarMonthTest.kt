package com.decagonhq.decapay.feature.createbudget.data.staticdata

import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Test

class CalendarMonthTest {

    @Test
    fun convertMonthStringValueToInt_firstMonthIsSelected() {
        val firstMonth = "January"
        val result = CalendarMonth.convertMonthStringValueToInt(firstMonth)
        assertEquals(1, result)
    }

    @Test
    fun convertMonthStringValueToInt_lastMonthIsSelected() {
        val lastMonth = "December"
        val result = CalendarMonth.convertMonthStringValueToInt(lastMonth)
        assertEquals(12, result)
    }
}
