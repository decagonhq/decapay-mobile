package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class EditBudgetLineItemFakeFlow(
    private val emittedResource: Resource<EditBudgetLineItemResponse>
) : Flow<Resource<EditBudgetLineItemResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<EditBudgetLineItemResponse>>) {
        collector.emit(emittedResource)
    }
}
