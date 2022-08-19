package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class CreateBudgetLineItemFakeFlow (
    private val emittedResource: Resource<CreateBudgetLineItemResponse>
        ) : Flow<Resource<CreateBudgetLineItemResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<CreateBudgetLineItemResponse>>) {
        collector.emit(emittedResource)
    }

}