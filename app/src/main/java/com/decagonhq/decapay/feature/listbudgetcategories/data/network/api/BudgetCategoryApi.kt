package com.decagonhq.decapay.feature.listbudgetcategories.data.network.api

import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import retrofit2.http.GET

interface BudgetCategoryApi {

    @GET("api/v1/budget_categories")
    suspend fun getBudgetCategories(

    ): BudgetCategoriesResponse
}