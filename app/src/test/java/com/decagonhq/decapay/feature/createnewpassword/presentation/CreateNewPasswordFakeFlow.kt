package com.decagonhq.decapay.feature.createnewpassword.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class CreateNewPasswordFakeFlow(
    private val emittedResource: Resource<CreateNewPasswordResponse>
) : Flow<Resource<CreateNewPasswordResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<CreateNewPasswordResponse>>) {
        collector.emit(emittedResource)
    }
}
