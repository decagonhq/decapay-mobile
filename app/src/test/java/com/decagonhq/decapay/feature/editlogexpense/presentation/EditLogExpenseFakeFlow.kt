package com.decagonhq.decapay.feature.editlogexpense.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class EditLogExpenseFakeFlow(
    private val emiitedResource: Resource<EditLogExpenseResponse>
) : Flow<Resource<EditLogExpenseResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<EditLogExpenseResponse>>) {
        collector.emit(emiitedResource)
    }
}