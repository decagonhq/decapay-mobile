package com.decagonhq.decapay.feature.editbudget.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class UpdateEditBudgetFakeFlow(
    private val emittedResource: Resource<UpdateBudgetResponse>
): Flow<Resource<UpdateBudgetResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<UpdateBudgetResponse>>) {
        collector.emit(emittedResource)
    }
}