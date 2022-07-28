package com.decagonhq.decapay.feature.login.data.network.model

data class LoginResponse(
    val `data`: Data?,
    val message: String?,
    val status: String?,
    val timestamp: String?
)