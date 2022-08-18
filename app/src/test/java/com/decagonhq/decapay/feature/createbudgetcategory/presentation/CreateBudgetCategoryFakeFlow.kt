package com.decagonhq.decapay.feature.createbudgetcategory.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class CreateBudgetCategoryFakeFlow(
    private val emittedResources: Resource<CreateBudgetCategoryResponse>
): Flow<Resource<CreateBudgetCategoryResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<CreateBudgetCategoryResponse>>) {
        collector.emit(emittedResources)
    }

}