package com.decagonhq.decapay.feature.createnewpassword.data.network.model



data class CreateNewPasswordResponse(
    val statusCode: String?,
    val success: Boolean?,
    val message: String?,
    val errors: String?
)
