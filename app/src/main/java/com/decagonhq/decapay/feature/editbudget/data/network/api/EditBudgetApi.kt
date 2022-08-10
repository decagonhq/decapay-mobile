package com.decagonhq.decapay.feature.editbudget.data.network.api

import retrofit2.http.PUT

interface EditBudgetApi {
    @PUT("api/v1/edit-budgets")
    suspend fun editBudget()
}
