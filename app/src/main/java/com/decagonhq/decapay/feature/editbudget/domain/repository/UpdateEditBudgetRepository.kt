package com.decagonhq.decapay.feature.editbudget.domain.repository

import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetRequestBody
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse

interface UpdateEditBudgetRepository {
    suspend fun updateBudget(
        updateBudgetRequestBody: UpdateBudgetRequestBody,
        budgetId: Int
    ): UpdateBudgetResponse
}