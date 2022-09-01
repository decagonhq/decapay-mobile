package com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse

interface GetBudgetCategoryListRepository {

    suspend fun getBudgetCategoryList(): GetBudgetCategoryListResponse
}
