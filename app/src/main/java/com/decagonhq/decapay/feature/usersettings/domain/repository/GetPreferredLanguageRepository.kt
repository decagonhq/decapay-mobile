package com.decagonhq.decapay.feature.usersettings.domain.repository

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredLanguageResponseDemo
import retrofit2.http.GET

interface GetPreferredLanguageRepository {
    @GET("api/v1/language")
    suspend fun getPreferredLanguage(): GetPreferredLanguageResponseDemo
}