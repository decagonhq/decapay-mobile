package com.decagonhq.decapay.feature.usersettings.data.network.model.register

data class RegisterRequestBody(
    val countryCode: String?,
    val currencyCode: String?,
    val email: String?,
    val firstName: String?,
    val languageCode: String?,
    val lastName: String?,
    val password: String?,
    val phoneNumber: String?
)