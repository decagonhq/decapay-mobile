package com.decagonhq.decapay.feature.createnewpassword.data.network.api

import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CreateNewPasswordApi {

    @POST("api/v1/reset-password")
    suspend fun createNewPassword(
        @Header(NetworkConstant.REQUEST_HEADER_NAME) key: String,
        @Body createNewPasswordRequest: CreateNewPasswordRequest
    ): CreateNewPasswordResponse
}
