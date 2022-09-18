package com.decagonhq.decapay.feature.changepassword.data.network.model

data class ChangePasswordRequestBody(
    val confirmPassword: String?,
    val newPassword: String?,
    val password: String?
)