package com.decagonhq.decapay.feature.editbudget.domain.repository

import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse

interface FetchUserBudgetToEditRepository {
    suspend fun fetchBudgetToEdit(
        budgetId: Int
    ): FetchEditBudgetResponse
}
