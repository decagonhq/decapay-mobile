package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.repository

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.api.CreateBudgetLineItemApi
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository.CreateBudgetLineItemRepository
import javax.inject.Inject

class CreateBudgetLineItemRepositoryImpl @Inject constructor(
    private val createBudgetLineItemApi: CreateBudgetLineItemApi
): CreateBudgetLineItemRepository {
    override suspend fun createBudgetLineItem(
        budgetId: Int,
        createBudgetLineItemRequestBody: CreateBudgetLineItemRequestBody
    ): CreateBudgetLineItemResponse {
        return createBudgetLineItemApi.createBudgetLineItem(budgetId, createBudgetLineItemRequestBody)
    }
}