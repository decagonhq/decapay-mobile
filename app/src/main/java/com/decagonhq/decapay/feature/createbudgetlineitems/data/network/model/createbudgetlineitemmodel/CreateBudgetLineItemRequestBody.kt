package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel

data class CreateBudgetLineItemRequestBody(
    val amount: Double?,
    val budgetCategoryId: Int?,
    val setLineItemAsTemplate: Boolean?
)