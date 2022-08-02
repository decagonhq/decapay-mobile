package com.decagonhq.decapay.feature.forgotpassword.presentation

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ForgotPasswordFakeFlow(
    private val emittedResource: Resource<ForgotPasswordResponse>
) : Flow<Resource<ForgotPasswordResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<ForgotPasswordResponse>>) {
        collector.emit(emittedResource)
    }


}