package com.decagonhq.decapay.feature.login.data.network.api

import com.decagonhq.decapay.feature.login.data.network.model.LoginRequestBody
import com.decagonhq.decapay.feature.login.data.network.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("api/v1/signin")
    suspend fun loginUser(
        @Body loginRequestBody: LoginRequestBody
    ): LoginResponse
}
