package com.decagonhq.decapay.feature.listbudget

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ResponseFakeFlow(
    private val emittedResource: Resource<BudgetListResponse>
) : Flow<Resource<BudgetListResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<BudgetListResponse>>) {
        collector.emit(emittedResource)
    }
}
