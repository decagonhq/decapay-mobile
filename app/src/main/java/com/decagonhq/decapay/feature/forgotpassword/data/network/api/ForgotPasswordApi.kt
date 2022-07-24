package com.decagonhq.decapay.feature.forgotpassword.data.network.api

import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordApi {

    @POST("/api/v1/Account/Forgot-Password")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): ForgotPasswordResponse
}
