package com.decagonhq.decapay.feature.logexpense.data.network.model

data class LogExpenseRequestBody(
    val amount: Double?,
    val description: String?,
    val transactionDate: String?
)