package com.decagonhq.decapay.feature.createbudget.data.network.model

data class CreateBudgetRequestBody(
    val id: Int?,
    val title: String,
    val projectedAmount: Double
)
