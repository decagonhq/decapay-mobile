package com.decagonhq.decapay.feature.editbudgetcategory.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class EditBudgetCategoryFakeFlow(
    private val emittedResource: Resource<EditBudgetCategoryResponse>
) : Flow<Resource<EditBudgetCategoryResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<EditBudgetCategoryResponse>>) {
        collector.emit(emittedResource)
    }
}