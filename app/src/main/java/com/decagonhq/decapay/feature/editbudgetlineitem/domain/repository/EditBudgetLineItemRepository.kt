package com.decagonhq.decapay.feature.editbudgetlineitem.domain.repository

import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse

interface EditBudgetLineItemRepository {
    suspend fun updateBudgetLineItem(
        budgetId: Int,
        categoryId: Int,
        editBudgetLineItemRequestBody: EditBudgetLineItemRequestBody
    ): EditBudgetLineItemResponse
}