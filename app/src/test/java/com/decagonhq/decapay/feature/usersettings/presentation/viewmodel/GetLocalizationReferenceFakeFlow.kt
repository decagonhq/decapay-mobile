package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class GetLocalizationReferenceFakeFlow(
    private val emittedResource: Resource<GetLocalizationReferenceResponse>
) : Flow<Resource<GetLocalizationReferenceResponse>>{
    override suspend fun collect(collector: FlowCollector<Resource<GetLocalizationReferenceResponse>>) {
        collector.emit(emittedResource)
    }
}