package com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.api

import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface VerifyPasswordResetCodeApi {
    @POST("api/v1/verify-code")
    suspend fun verifyPasswordResetCode(
        @Body verifyPasswordResetCodeRequest: VerifyPasswordResetCodeRequest
    ): VerifyPasswordResetCodeResponse
}
