package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.api

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CreateBudgetLineItemApi {
    @POST("api/v1/budgets/{budgetId}/lineItems")
    suspend fun createBudgetLineItem(
        @Path("budgetId") budgetId: Int,
        @Body createBudgetLineItemRequestBody: CreateBudgetLineItemRequestBody
    ): CreateBudgetLineItemResponse
}