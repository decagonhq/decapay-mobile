package com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model

data class VerifyPasswordResetCodeRequest(
    val email: String?,
    val resetCode: String?
)
