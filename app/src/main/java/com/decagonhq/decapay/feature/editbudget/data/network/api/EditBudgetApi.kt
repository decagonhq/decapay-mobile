package com.decagonhq.decapay.feature.editbudget.data.network.api

import retrofit2.http.GET
import retrofit2.http.PUT

interface EditBudgetApi {

    @GET("api/v1/budgets/edit/{budgetId}")
    suspend fun fetchBudgetToEdit()

    @PUT("api/v1/budgets/{budgetId}")
    suspend fun editBudget()
}
