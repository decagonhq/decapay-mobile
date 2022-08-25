package com.decagonhq.decapay.feature.logexpense.data.network.repository

import com.decagonhq.decapay.feature.logexpense.data.network.api.LogExpenseApi
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBodyDemo
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo
import com.decagonhq.decapay.feature.logexpense.domain.repository.LogExpenseRepository
import javax.inject.Inject

class LogExpenseRepositoryImpl @Inject constructor(
    private val logExpenseApi: LogExpenseApi
) : LogExpenseRepository {
    override suspend fun addExpense(logExpenseRequestBodyDemo: LogExpenseRequestBodyDemo): LogExpenseResponseDemo {
        return logExpenseApi.addExpense(logExpenseRequestBodyDemo)
    }
}