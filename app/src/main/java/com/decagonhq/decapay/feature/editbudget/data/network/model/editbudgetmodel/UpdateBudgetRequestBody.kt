package com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel

data class UpdateBudgetRequestBody(
    val amount: Double?,
    val budgetEndDate: String?,
    val budgetStartDate: String?,
    val description: String?,
    val duration: Int?,
    val month: Int?,
    val period: String?,
    val title: String?,
    val year: Int?
)