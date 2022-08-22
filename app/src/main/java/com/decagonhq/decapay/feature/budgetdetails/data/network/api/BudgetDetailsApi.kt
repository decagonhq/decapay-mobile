package com.decagonhq.decapay.feature.budgetdetails.data.network.api

import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.DeleteLineItemResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BudgetDetailsApi {

    @GET("api/v1/budgets/{budgetId}")
    suspend fun getBudgetDetails(
        @Path("budgetId") budgetId: Int,
        ): BudgetDetailsResponse

    @DELETE("api/v1/budgets/{budgetId}/lineItems/{categoryId}")
    suspend fun deleteLineItem(
        @Path("budgetId") budgetId: Int,
        @Path("categoryId") categoryId : Int,
        ): Response<Void>
}
