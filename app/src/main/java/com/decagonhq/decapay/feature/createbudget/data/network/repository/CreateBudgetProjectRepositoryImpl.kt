package com.decagonhq.decapay.feature.createbudget.data.network.repository

import com.decagonhq.decapay.feature.createbudget.data.network.api.CreateBudgetApi
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse
import com.decagonhq.decapay.feature.createbudget.domain.repository.CreateBudgetRepository
import javax.inject.Inject

class CreateBudgetProjectRepositoryImpl @Inject constructor(
    private val createBudgetApi: CreateBudgetApi
) : CreateBudgetRepository {
    override suspend fun createBudget(createBudgetRequestBody: CreateBudgetRequestBody): CreateBudgetResponse {
        return createBudgetApi.createBudget(createBudgetRequestBody)
    }
}
