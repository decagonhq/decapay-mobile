package com.decagonhq.decapay.feature.createbudget.data.network.api

import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateBudgetApi {

    @POST("api/v1/budgets")
    suspend fun createBudget(
        @Body createBudgetRequestBody: CreateBudgetRequestBody
    ): CreateBudgetResponse
}
