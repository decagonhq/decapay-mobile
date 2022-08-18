package com.decagonhq.decapay.feature.listbudgetcategories.data.network.api

import retrofit2.http.GET

interface BudgetCategoryApi {

    @GET("api/v1/budgets")
    suspend fun getBudgetCategories(

    ): Any
}