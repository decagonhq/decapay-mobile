package com.decagonhq.decapay.common.utils.converterhelper

import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Test

class UtilsConverterTest {

    @Test
    fun addOneDayFormat_whenNullInputGiven() {
        val input = ""
        val result = UtilsConverter.addOneDayFormat(input)
        assertEquals("", result)
    }

    @Test
    fun formatCurrentDate_whenCurentDateGiven() {
        val currentDate ="30/08/2022"
        val expected = "2022-08-30"
        val output = UtilsConverter.formatCurrentDate(currentDate)
        assertEquals(expected, output)
    }

    @Test
    fun formatReceivedTransactionDate_whenTransactionDateForUpdateIsReceived() {
        val transactionDate = "2022-09-01"
        val expected = "01/09/2022"
        val output = UtilsConverter.formatReceivedTransactionDate(transactionDate)
        assertEquals(expected, output)
    }
}
