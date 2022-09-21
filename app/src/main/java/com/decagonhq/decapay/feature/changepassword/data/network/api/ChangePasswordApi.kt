package com.decagonhq.decapay.feature.changepassword.data.network.api

import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordRequestBody
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChangePasswordApi {
    @POST("api/v1/profile/changePassword")
    suspend fun changePassword(
        @Body changePasswordRequestBody: ChangePasswordRequestBody
    ): ChangePasswordResponse

}