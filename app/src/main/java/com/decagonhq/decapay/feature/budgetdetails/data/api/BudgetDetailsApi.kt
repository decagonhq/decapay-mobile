package com.decagonhq.decapay.feature.budgetdetails.data.api

import retrofit2.http.GET

interface BudgetDetailApi {

    @GET("api/v1/")
    suspend fun getBudgetDetails(
    ): Any
}
