package com.decagonhq.decapay.feature.createbudget.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class CreateBudgetFakeFlow(
    private val emittedResource: Resource<CreateBudgetResponse>
) : Flow<Resource<CreateBudgetResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<CreateBudgetResponse>>) {
        collector.emit(emittedResource)
    }
}