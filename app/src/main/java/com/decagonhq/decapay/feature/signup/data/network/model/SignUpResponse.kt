package com.decagonhq.decapay.feature.signup.data.network.model

data class SignUpResponse (
    val `data`: Data?,
    val debugMessage: String?,
    val errorCode: String?,
    val message: String?,
    val status: String?,
    val subErrors: List<SubError>?,
    val timestamp: String?
        )