package com.decagonhq.decapay.feature.editbudget.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class FetchEditBudgetFakeFlow(
    private val emittedResources: Resource<FetchEditBudgetResponse>
): Flow<Resource<FetchEditBudgetResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<FetchEditBudgetResponse>>) {
        collector.emit(emittedResources)
    }
}