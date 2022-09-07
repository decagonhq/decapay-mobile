package com.decagonhq.decapay.feature.usersettings.data.network.repository

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetLocalizationReferenceApi
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetLocalizationReferenceRepository
import javax.inject.Inject

class GetLocalizationReferenceRepositoryImpl @Inject constructor(
    private val getLocalizationReferenceApi: GetLocalizationReferenceApi
) : GetLocalizationReferenceRepository {
    override suspend fun getLocalizationReference(): GetLocalizationReferenceResponse {
        return getLocalizationReferenceApi.getLocalizationReferenceApi()
    }
}
