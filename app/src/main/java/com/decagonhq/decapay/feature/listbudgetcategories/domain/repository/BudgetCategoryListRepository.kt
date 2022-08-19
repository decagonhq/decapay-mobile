package com.decagonhq.decapay.feature.listbudgetcategories.domain.repository

import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse

interface BudgetCategoryListRepository {

    suspend fun  getBudgetCategories(): BudgetCategoriesResponse

}