package com.decagonhq.decapay.feature.createnewpassword.data.network.model

data class CreateNewPasswordRequest(
    val email: String?,
    val token: String?,
    val newPassword: String?,
    val confirmPassword: String?
)
