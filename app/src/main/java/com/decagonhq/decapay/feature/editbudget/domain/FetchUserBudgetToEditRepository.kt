package com.decagonhq.decapay.feature.editbudget.domain

import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse

interface FetchUserBudgetToEditRepository {
    suspend fun fetchBudgetToEdit(
        budgetId: Int
    ): FetchEditBudgetResponse
}
