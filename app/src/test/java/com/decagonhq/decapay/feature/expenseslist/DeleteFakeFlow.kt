package com.decagonhq.decapay.feature.expenseslist

import com.decagonhq.decapay.common.utils.resource.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class DeleteFakeFlow( private val emittedResource: Resource<Any>
) : Flow<Resource<Any>> {
    override suspend fun collect(collector: FlowCollector<Resource<Any>>) {
        collector.emit(emittedResource)
    }
}