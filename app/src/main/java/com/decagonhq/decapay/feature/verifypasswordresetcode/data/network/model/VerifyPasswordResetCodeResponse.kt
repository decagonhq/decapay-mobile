package com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model

data class VerifyPasswordResetCodeResponse(
    val `data`: Data?,
    val debugMessage: String?,
    val errorCode: String?,
    val message: String?,
    val status: String?,
    val subErrors: List<SubError?>?,
    val timestamp: String?
)