package com.decagonhq.decapay.feature.logexpense.data.network.api

import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LogExpenseApi {
    @POST("api/v1/budgets/{budgetId}/lineItems/{categoryId}/expenses")
    suspend fun addExpense(
        @Path("budgetId") budgetId: Int,
        @Path("categoryId") categoryId: Int,
        @Body logExpenseRequestBody: LogExpenseRequestBody
    ): LogExpenseResponse
}
