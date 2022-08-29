package com.decagonhq.decapay.feature.editlogexpense.data.network.model

data class EditLogExpenseRequestBody(
    val amount: Int?,
    val description: String?,
    val transactionDate: String?
)