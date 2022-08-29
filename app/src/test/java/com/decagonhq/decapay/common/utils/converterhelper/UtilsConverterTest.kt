package com.decagonhq.decapay.common.utils.converterhelper

import org.junit.Assert.*
import org.junit.Test

class UtilsConverterTest {

    @Test
    fun addOneDayFormat_whenNullInputGiven() {
        val input = ""
        val result = UtilsConverter.addOneDayFormat(input)
        assertEquals("", result)
    }

}