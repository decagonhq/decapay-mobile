package com.decagonhq.decapay.feature.logexpense.data.network.api

import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBodyDemo
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo
import retrofit2.http.Body
import retrofit2.http.POST

interface LogExpenseApi {
    @POST("api/v1/budgets/{budgetId}/expense")
    suspend fun addExpense(
        @Body logExpenseRequestBodyDemo: LogExpenseRequestBodyDemo
    ): LogExpenseResponseDemo
}