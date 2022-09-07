package com.decagonhq.decapay.feature.usersettings.data.network.api

import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterRequestBody
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("api/v1/register")
    suspend fun registerUser(
        @Body registerRequestBody: RegisterRequestBody
    ): RegisterResponse
}
