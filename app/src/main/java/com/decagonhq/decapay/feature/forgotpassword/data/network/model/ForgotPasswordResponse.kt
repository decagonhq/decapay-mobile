package com.decagonhq.decapay.feature.forgotpassword.data.network.model

data class ForgotPasswordResponse(
    val statusCode: String,
    val successful: Boolean,
    val message: String?,
    val errors: String?
)
