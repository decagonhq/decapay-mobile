package com.decagonhq.decapay.feature.budgetdetails

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ResponseFakeFlow (
    private val emittedResource :Resource<BudgetDetailsResponse>
        ): Flow<Resource<BudgetDetailsResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<BudgetDetailsResponse>>) {
        collector.emit(emittedResource)
    }
}