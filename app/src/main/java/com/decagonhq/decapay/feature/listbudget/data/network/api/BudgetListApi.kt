package com.decagonhq.decapay.feature.listbudget.data.network.api

import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BudgetListApi {

    @GET("api/v1/budgets")
    suspend fun getBudgetList(
        @Header("Authorization") authorization: String,
        ): BudgetListResponse
}