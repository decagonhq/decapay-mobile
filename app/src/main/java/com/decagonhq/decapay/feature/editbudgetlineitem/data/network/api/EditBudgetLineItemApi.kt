package com.decagonhq.decapay.feature.editbudgetlineitem.data.network.api

import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditBudgetLineItemApi {
    @PUT("api/v1/budgets/{budgetId}/lineItems/{categoryId}")
    suspend fun updateBudgetLineItem(
        @Path("budgetId") budgetId: Int,
        @Path("categoryId") categoryId: Int,
        @Body editBudgetLineItemRequestBody: EditBudgetLineItemRequestBody
    ): EditBudgetLineItemResponse
}