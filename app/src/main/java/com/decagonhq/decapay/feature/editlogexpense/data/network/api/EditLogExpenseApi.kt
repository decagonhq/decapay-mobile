package com.decagonhq.decapay.feature.editlogexpense.data.network.api

import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditLogExpenseApi {
    @PUT("api/v1/expenses/{expenseId}")
    suspend fun updateLogExpense(
        @Path("expenseId") expenseId: Int,
        @Body editLogExpenseRequestBody: EditLogExpenseRequestBody
    ): EditLogExpenseResponse
}
