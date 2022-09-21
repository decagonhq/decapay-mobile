package com.decagonhq.decapay.feature.changepassword.data.network.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestBody(
    @SerializedName("confirmNewPassword")
    val confirmNewPassword: String?,
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("password")
    val password: String?
)