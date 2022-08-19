package com.decagonhq.decapay.feature.createbudgetcategory.data.network.repository

import com.decagonhq.decapay.feature.createbudgetcategory.data.network.api.CreateBudgetCategoryApi
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse
import com.decagonhq.decapay.feature.createbudgetcategory.domain.repository.CreateBudgetCategoryRepository
import javax.inject.Inject

class CreateBudgetCategoryRepositoryImpl @Inject constructor(
    private val createBudgetCategoryApi: CreateBudgetCategoryApi
) : CreateBudgetCategoryRepository {
    override suspend fun createBudgetCategoryRepository(createBudgetCategoryRequestBody: CreateBudgetCategoryRequestBody): CreateBudgetCategoryResponse {
        return createBudgetCategoryApi.createBudgetCategory(createBudgetCategoryRequestBody)
    }
}
