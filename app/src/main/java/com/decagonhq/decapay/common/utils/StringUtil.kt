package com.decagonhq.decapay.common.utils

import java.text.NumberFormat
import java.util.*


fun Double.commaSeparatedString():String{
    val country = "US"
    val language = "en"
    val finalOutput: String = NumberFormat.getCurrencyInstance(Locale(language, country)).format(this)
    return finalOutput.substring(1)

}