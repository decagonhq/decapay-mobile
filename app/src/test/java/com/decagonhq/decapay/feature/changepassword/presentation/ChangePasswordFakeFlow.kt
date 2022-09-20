package com.decagonhq.decapay.feature.changepassword.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ChangePasswordFakeFlow(
    private val emittedResource: Resource<ChangePasswordResponse>
) : Flow<Resource<ChangePasswordResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<ChangePasswordResponse>>) {
        collector.emit(emittedResource)
    }
}