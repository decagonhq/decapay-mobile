package com.decagonhq.decapay.feature.usersettings.data.network.api

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse
import retrofit2.http.GET

interface GetLocalizationReferenceApi {

    @GET("api/v1/references")
    suspend fun getLocalizationReferenceApi(): GetLocalizationReferenceResponse
}