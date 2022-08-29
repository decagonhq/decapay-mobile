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
}
