package com.decagonhq.decapay.feature.logexpense.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class LogExpenseFakeFlow(
    private val emittedResource: Resource<LogExpenseResponseDemo>
) : Flow<Resource<LogExpenseResponseDemo>>{
    override suspend fun collect(collector: FlowCollector<Resource<LogExpenseResponseDemo>>) {
        collector.emit(emittedResource)
    }
}