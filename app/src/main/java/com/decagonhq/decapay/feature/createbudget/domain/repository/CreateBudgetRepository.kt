package com.decagonhq.decapay.feature.createbudget.domain.repository

import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse

interface CreateBudgetRepository {
    suspend fun createBudget(
        createBudgetRequestBody: CreateBudgetRequestBody
    ): CreateBudgetResponse
}
