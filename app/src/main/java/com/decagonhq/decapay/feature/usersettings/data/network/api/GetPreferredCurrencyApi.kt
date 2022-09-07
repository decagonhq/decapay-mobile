package com.decagonhq.decapay.feature.usersettings.data.network.api

import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredCurrencyResponseDemo
import retrofit2.http.GET

interface GetPreferredCurrencyApi {

    @GET("api/v1/currency")
    suspend fun getPreferredCurrency(): GetPreferredCurrencyResponseDemo
}
