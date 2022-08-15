package com.decagonhq.decapay.feature.editbudget.data.network.repository

import com.decagonhq.decapay.feature.editbudget.data.network.api.EditBudgetApi
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetRequestBody
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.repository.UpdateEditBudgetRepository
import javax.inject.Inject

class UpdateEditBudgetRepositoryImpl @Inject constructor(
    private val editBudgetApi: EditBudgetApi
): UpdateEditBudgetRepository {
    override suspend fun updateBudget(
        updateBudgetRequestBody: UpdateBudgetRequestBody,
        budgetId: Int
    ): UpdateBudgetResponse {
        return editBudgetApi.editBudget(updateBudgetRequestBody, budgetId)
    }
}