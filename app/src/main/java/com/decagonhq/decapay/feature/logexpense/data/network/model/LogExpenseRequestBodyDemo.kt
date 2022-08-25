package com.decagonhq.decapay.feature.logexpense.data.network.model

data class LogExpenseRequestBodyDemo(
    val amountSpent: Double?,
    val category: String?,
    val dateChosen: String?,
    val description: String?
)