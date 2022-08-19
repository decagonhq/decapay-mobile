package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.repository

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.api.GetBudgetCategoryListApi
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository.GetBudgetCategoryListRepository
import javax.inject.Inject

class GetBudgetCategoryListRepositoryImpl @Inject constructor(
    private val getBudgetCategoryListApi: GetBudgetCategoryListApi
): GetBudgetCategoryListRepository {
    override suspend fun getBudgetCategoryList(): GetBudgetCategoryListResponse {
        return getBudgetCategoryListApi.getBudgetCategoryList()
    }
}