package com.decagonhq.decapay.feature.createnewpassword.data.network.model

data class CreateNewPasswordRequest(
    val confirmPassword: String?,
    val password: String?,
    val token: String?
)
