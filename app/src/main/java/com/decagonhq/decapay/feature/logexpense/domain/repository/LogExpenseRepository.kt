package com.decagonhq.decapay.feature.logexpense.domain.repository

import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponse

interface LogExpenseRepository {

    suspend fun addExpense(
        budgetId: Int,
        categoryId: Int,
        logExpenseRequestBody: LogExpenseRequestBody
    ): LogExpenseResponse
}
