package com.decagonhq.decapay.feature.createbudgetcategory.domain.repository

import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse

interface CreateBudgetCategoryRepository {

    suspend fun createBudgetCategoryRepository(
        createBudgetCategoryRequestBody: CreateBudgetCategoryRequestBody
    ): CreateBudgetCategoryResponse
}
