package com.decagonhq.decapay.feature.login.data.network.model.localization

data class Localization(
    val `data`: Data?,
    val debugMessage: Any?,
    val errorCode: Any?,
    val message: String?,
    val status: String?,
    val subErrors: Any?,
    val timestamp: String?
)