package com.decagonhq.decapay.feature.logexpense.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class LogExpenseFakeFlow(
    private val emittedResource: Resource<LogExpenseResponse>
) : Flow<Resource<LogExpenseResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<LogExpenseResponse>>) {
        collector.emit(emittedResource)
    }
}
