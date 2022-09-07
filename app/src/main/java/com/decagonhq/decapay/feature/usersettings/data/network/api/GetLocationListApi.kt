package com.decagonhq.decapay.feature.usersettings.data.network.api

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocationResponseDemo
import retrofit2.http.GET

interface GetLocationListApi {

    @GET("api/v1/location")
    suspend fun getLocationList(): GetLocationResponseDemo
}