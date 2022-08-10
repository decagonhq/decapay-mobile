package com.decagonhq.decapay.feature.editbudget.data.network.model

data class EditBudgetRequestBody(
    val title: String?,
    val amount: Double?,
    val budgetPeriod: String?
)