package com.decagonhq.decapay.feature.budgetdetails.data.network.api

import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BudgetDetailsApi {

    @GET("api/v1/budgets/{budgetId}")
    suspend fun getBudgetDetails(
        @Path("budgetId") budgetId: Int,
        @Header("Authorization") authorization: String,
        ): BudgetDetailsResponse
}
