package com.decagonhq.decapay.feature.listbudget.data.network.api

import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BudgetListApi {

    @GET("api/v1/budgets")
    suspend fun getBudgetList(
        @Query("page") address: Int,
        @Query("size") size: Int,
        @Query("state") state: String,
    ): BudgetListResponse
}
