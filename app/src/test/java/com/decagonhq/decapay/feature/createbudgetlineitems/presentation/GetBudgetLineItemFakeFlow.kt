package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class GetBudgetLineItemFakeFlow(
    private val emittedResource: Resource<GetBudgetCategoryListResponse>
) : Flow<Resource<GetBudgetCategoryListResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<GetBudgetCategoryListResponse>>) {
        collector.emit(emittedResource)
    }
}