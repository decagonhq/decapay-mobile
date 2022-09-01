package com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse

interface CreateBudgetLineItemRepository {
    suspend fun createBudgetLineItem(
        budgetId: Int,
        createBudgetLineItemRequestBody: CreateBudgetLineItemRequestBody
    ): CreateBudgetLineItemResponse
}
