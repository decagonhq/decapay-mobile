package com.decagonhq.decapay.feature.signup.data.network.api

import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {

    @POST("api/v1/register")
    suspend fun signUp(
        @Body signUpRequestBody: SignUpRequestBody
    ): SignUpResponse
}
