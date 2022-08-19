package com.decagonhq.decapay.feature.listbudgetcategories.data.repository

import com.decagonhq.decapay.feature.listbudgetcategories.data.network.api.BudgetCategoryApi
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import com.decagonhq.decapay.feature.listbudgetcategories.domain.repository.BudgetCategoryListRepository
import javax.inject.Inject

class BudgetCategoryListRepositoryImpl @Inject constructor(
    private val budgetCategoryApi: BudgetCategoryApi
) : BudgetCategoryListRepository {


    override suspend fun getBudgetCategories(): BudgetCategoriesResponse {
        return budgetCategoryApi.getBudgetCategories()
    }
}