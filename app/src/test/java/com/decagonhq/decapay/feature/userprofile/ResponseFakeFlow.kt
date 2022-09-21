package com.decagonhq.decapay.feature.userprofile

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ResponseFakeFlow (
    private val emittedResource :Resource<UserProfileResponse>
        ): Flow<Resource<UserProfileResponse>> {
    override suspend fun collect(collector: FlowCollector<Resource<UserProfileResponse>>) {
        collector.emit(emittedResource)
    }
}