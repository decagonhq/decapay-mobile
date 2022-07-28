package com.decagonhq.decapay.feature.forgotpassword.data.network.repository

import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.feature.forgotpassword.data.network.api.ForgotPasswordApi
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import com.decagonhq.decapay.feature.forgotpassword.domain.repository.ForgotPasswordRepository
import javax.inject.Inject

class ForgotPasswordRepositoryImpl @Inject constructor(
    private val forgotPasswordApi: ForgotPasswordApi
) : ForgotPasswordRepository {
    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse {
        return forgotPasswordApi.forgotPassword(NetworkConstant.FORGOT_PASSWORD_HEADER_KEY, forgotPasswordRequest)
    }
}
