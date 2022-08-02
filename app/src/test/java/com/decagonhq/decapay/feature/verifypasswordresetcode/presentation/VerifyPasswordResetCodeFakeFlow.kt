package com.decagonhq.decapay.feature.verifypasswordresetcode.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class VerifyPasswordResetCodeFakeFlow(
    private val emittedResource: Resource<VerifyPasswordResetCodeResponse>
) : Flow<Resource<VerifyPasswordResetCodeResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<VerifyPasswordResetCodeResponse>>) {
        collector.emit(emittedResource)
    }
}