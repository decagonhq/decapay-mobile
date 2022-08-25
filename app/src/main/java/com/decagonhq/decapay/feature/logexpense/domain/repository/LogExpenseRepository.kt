package com.decagonhq.decapay.feature.logexpense.domain.repository

import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBodyDemo
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo

interface LogExpenseRepository {

    suspend fun addExpense(
        logExpenseRequestBodyDemo: LogExpenseRequestBodyDemo
    ): LogExpenseResponseDemo
}
