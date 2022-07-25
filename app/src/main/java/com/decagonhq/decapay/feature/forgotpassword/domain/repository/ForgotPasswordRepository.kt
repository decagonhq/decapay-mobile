package com.decagonhq.decapay.feature.forgotpassword.domain.repository

import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse

interface ForgotPasswordRepository {

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse
}
