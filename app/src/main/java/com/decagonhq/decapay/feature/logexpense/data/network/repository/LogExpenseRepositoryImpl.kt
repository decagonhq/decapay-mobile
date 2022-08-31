package com.decagonhq.decapay.feature.logexpense.data.network.repository

import com.decagonhq.decapay.feature.logexpense.data.network.api.LogExpenseApi
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponse
import com.decagonhq.decapay.feature.logexpense.domain.repository.LogExpenseRepository
import javax.inject.Inject

class LogExpenseRepositoryImpl @Inject constructor(
    private val logExpenseApi: LogExpenseApi
) : LogExpenseRepository {
    override suspend fun addExpense(budgetId: Int, categoryId: Int, logExpenseRequestBody: LogExpenseRequestBody): LogExpenseResponse {
        return logExpenseApi.addExpense(budgetId, categoryId, logExpenseRequestBody)
    }
}
