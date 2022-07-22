package com.decagonhq.decapay.feature.signup

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class FakeFlow (
   private val emittedResource : Resource<SignUpResponse>
): Flow<Resource<SignUpResponse>> {


    override suspend fun collect(collector: FlowCollector<Resource<SignUpResponse>>) {
        collector.emit(emittedResource)
    }
}