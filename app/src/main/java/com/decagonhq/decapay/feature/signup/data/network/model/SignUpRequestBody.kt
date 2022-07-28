package com.decagonhq.decapay.feature.signup.data.network.model

data class SignUpRequestBody(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,

)