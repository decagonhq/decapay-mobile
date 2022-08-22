package com.decagonhq.decapay.feature.editbudgetcategory.domain.repository

import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse

interface EditBudgetCategoryRepository {
    suspend fun updateBudgetCategory(
        categoryId: Int,
        editBudgetCategoryRequestBody: EditBudgetCategoryRequestBody
    ): EditBudgetCategoryResponse
}
