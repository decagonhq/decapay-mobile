package com.decagonhq.decapay.feature.expenseslist

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ResponseFakeFlow(
    private val emittedResource: Resource<ExpenseListResponse>
) : Flow<Resource<ExpenseListResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<ExpenseListResponse>>) {
        collector.emit(emittedResource)
    }
}
