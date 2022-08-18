package com.decagonhq.decapay.feature.listbudgetcategories.domain.repository

interface BudgetCategoryListRepository {

    suspend fun  getBudgetCategories():Any

}