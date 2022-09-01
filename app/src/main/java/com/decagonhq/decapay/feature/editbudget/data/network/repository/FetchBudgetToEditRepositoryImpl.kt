package com.decagonhq.decapay.feature.editbudget.data.network.repository

import com.decagonhq.decapay.feature.editbudget.data.network.api.EditBudgetApi
import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.repository.FetchUserBudgetToEditRepository
import javax.inject.Inject

class FetchBudgetToEditRepositoryImpl @Inject constructor(
    private val editBudgetApi: EditBudgetApi
): FetchUserBudgetToEditRepository {
    override suspend fun fetchBudgetToEdit(budgetId: Int): FetchEditBudgetResponse {
        return editBudgetApi.fetchBudgetToEdit(budgetId)
    }
}