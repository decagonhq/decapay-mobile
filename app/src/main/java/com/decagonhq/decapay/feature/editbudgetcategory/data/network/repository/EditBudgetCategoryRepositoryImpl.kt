package com.decagonhq.decapay.feature.editbudgetcategory.data.network.repository

import com.decagonhq.decapay.feature.editbudgetcategory.domain.repository.EditBudgetCategoryRepository
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.api.EditBudgetCategoryApi
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse
import javax.inject.Inject

class EditBudgetCategoryRepositoryImpl @Inject constructor(
    private val editBudgetCategoryApi: EditBudgetCategoryApi
) : EditBudgetCategoryRepository {
    override suspend fun updateBudgetCategory(categoryId: Int, editBudgetCategoryRequestBody: EditBudgetCategoryRequestBody): EditBudgetCategoryResponse {
        return editBudgetCategoryApi.updateBudgetCategory(categoryId, editBudgetCategoryRequestBody)
    }
}