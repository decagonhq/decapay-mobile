package com.decagonhq.decapay.feature.forgotpassword.data.network.api

import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ForgotPasswordApi {

    @POST("api/v1/forgot-password")
    suspend fun forgotPassword(
        @Header(NetworkConstant.REQUEST_HEADER_NAME) key: String,
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): ForgotPasswordResponse
}
