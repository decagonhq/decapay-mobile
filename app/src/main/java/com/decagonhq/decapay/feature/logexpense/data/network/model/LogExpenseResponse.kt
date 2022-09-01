package com.decagonhq.decapay.feature.logexpense.data.network.model

data class LogExpenseResponse(
    val `data`: Data?,
    val message: String?,
    val status: String?,
    val timestamp: String?
)