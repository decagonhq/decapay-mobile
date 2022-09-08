package com.decagonhq.decapay.feature.usersettings.domain.repository

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse

interface GetLocalizationReferenceRepository {
    suspend fun getLocalizationReference(): GetLocalizationReferenceResponse
}
