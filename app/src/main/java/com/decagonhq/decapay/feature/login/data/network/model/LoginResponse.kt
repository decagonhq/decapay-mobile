package com.decagonhq.decapay.feature.login.data.network.model

data class LoginResponse(
    val message: String?,
    val token: String?,
    val error: String?
)
