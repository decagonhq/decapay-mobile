package com.decagonhq.decapay.feature.usersettings.data.network.model.register

data class RegisterResponse(
    val `data`: Data?,
    val debugMessage: Any?,
    val errorCode: Any?,
    val message: String?,
    val status: String?,
    val subErrors: Any?,
    val timestamp: String?
)