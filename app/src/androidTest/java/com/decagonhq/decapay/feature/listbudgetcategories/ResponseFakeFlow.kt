package com.decagonhq.decapay.feature.listbudgetcategories

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ResponseFakeFlow(
    private val emittedResource: Resource<BudgetCategoriesResponse>
) : Flow<Resource<BudgetCategoriesResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<BudgetCategoriesResponse>>) {
        collector.emit(emittedResource)
    }
}
