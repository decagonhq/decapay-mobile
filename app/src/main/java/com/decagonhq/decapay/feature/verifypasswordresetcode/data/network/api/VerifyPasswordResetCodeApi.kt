package com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.api

import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponseOld
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface VerifyPasswordResetCodeApi {
    @POST("api/v1/verify-code")
    suspend fun verifyPasswordResetCode(
        @Header(NetworkConstant.REQUEST_HEADER_NAME) key: String,
        @Body verifyPasswordResetCodeRequest: VerifyPasswordResetCodeRequest
    ): VerifyPasswordResetCodeResponse
}
