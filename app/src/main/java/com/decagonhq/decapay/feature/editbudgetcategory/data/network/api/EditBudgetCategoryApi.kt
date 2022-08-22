package com.decagonhq.decapay.feature.editbudgetcategory.data.network.api

import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditBudgetCategoryApi {

    @PUT("api/v1/budget_categories/{categoryId}")
    suspend fun updateBudgetCategory(
        @Path("categoryId") categoryId: Int,
        @Body editBudgetCategoryRequestBody: EditBudgetCategoryRequestBody
    ): EditBudgetCategoryResponse
}
