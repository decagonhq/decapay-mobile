package com.decagonhq.decapay.feature.editbudget.data.network.api

import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditBudgetApi {

    @GET("api/v1/budgets/edit/{budgetId}")
    suspend fun fetchBudgetToEdit(
        @Path("budgetId") budgetId: Int
    ): FetchEditBudgetResponse

    @PUT("api/v1/budgets/{budgetId}")
    suspend fun editBudget()
}
