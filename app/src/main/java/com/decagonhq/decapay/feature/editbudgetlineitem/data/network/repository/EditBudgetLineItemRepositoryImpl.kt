package com.decagonhq.decapay.feature.editbudgetlineitem.data.network.repository

import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.api.EditBudgetLineItemApi
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse
import com.decagonhq.decapay.feature.editbudgetlineitem.domain.repository.EditBudgetLineItemRepository
import javax.inject.Inject

class EditBudgetLineItemRepositoryImpl @Inject constructor(
    private val editBudgetLineItemApi: EditBudgetLineItemApi
) : EditBudgetLineItemRepository {
    override suspend fun updateBudgetLineItem(
        budgetId: Int,
        categoryId: Int,
        editBudgetLineItemRequestBody: EditBudgetLineItemRequestBody
    ): EditBudgetLineItemResponse {
        return editBudgetLineItemApi.updateBudgetLineItem(budgetId, categoryId, editBudgetLineItemRequestBody)
    }
}