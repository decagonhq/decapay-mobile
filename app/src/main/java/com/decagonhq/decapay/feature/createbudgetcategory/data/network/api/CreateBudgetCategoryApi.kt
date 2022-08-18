package com.decagonhq.decapay.feature.createbudgetcategory.data.network.api

import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateBudgetCategoryApi {

    @POST("api/v1/budget_categories")
    suspend fun createBudgetCategory(
        @Body createBudgetCategoryRequestBody: CreateBudgetCategoryRequestBody
    ): CreateBudgetCategoryResponse
}
