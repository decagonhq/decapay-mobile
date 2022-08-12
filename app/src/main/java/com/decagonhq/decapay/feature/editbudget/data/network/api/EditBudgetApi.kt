package com.decagonhq.decapay.feature.editbudget.data.network.api

import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetRequestBody
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditBudgetApi {

    @GET("api/v1/budgets/edit/{budgetId}")
    suspend fun fetchBudgetToEdit(
        @Path("budgetId") budgetId: Int
    ): FetchEditBudgetResponse

    @PUT("api/v1/budgets/{budgetId}")
    suspend fun editBudget(
        @Body updateBudgetRequestBody: UpdateBudgetRequestBody,
        @Path("budgetId") budgetId: Int
    ): UpdateBudgetResponse
}
