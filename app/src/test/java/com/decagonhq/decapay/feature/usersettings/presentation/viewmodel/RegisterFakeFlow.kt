package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class RegisterFakeFlow(
    private val emittedResource: Resource<RegisterResponse>
) : Flow<Resource<RegisterResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<RegisterResponse>>) {
        collector.emit(emittedResource)
    }
}