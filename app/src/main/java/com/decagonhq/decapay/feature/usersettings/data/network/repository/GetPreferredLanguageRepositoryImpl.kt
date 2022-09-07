package com.decagonhq.decapay.feature.usersettings.data.network.repository

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetPreferedLanguageApi
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredLanguageResponseDemo
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetPreferredLanguageRepository
import javax.inject.Inject

class GetPreferredLanguageRepositoryImpl @Inject constructor(
    private val getPreferedLanguageApi: GetPreferedLanguageApi
): GetPreferredLanguageRepository {
    override suspend fun getPreferredLanguage(): GetPreferredLanguageResponseDemo {
        return getPreferedLanguageApi.getPreferredLanguageApi()
    }
}