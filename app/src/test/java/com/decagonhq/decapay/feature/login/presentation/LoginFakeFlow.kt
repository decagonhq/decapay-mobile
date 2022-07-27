package com.decagonhq.decapay.feature.login.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.login.data.network.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class LoginFakeFlow(
    private val emittedResource: Resource<LoginResponse>
): Flow<Resource<LoginResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<LoginResponse>>) {
        collector.emit(emittedResource)
    }
}