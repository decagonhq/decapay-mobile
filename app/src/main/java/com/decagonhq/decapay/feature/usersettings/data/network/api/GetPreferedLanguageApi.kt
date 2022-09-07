package com.decagonhq.decapay.feature.usersettings.data.network.api

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredLanguageResponseDemo
import retrofit2.http.GET

interface GetPreferedLanguageApi {

    @GET("api/v1/language")
    suspend fun getPreferredLanguageApi(): GetPreferredLanguageResponseDemo
}
