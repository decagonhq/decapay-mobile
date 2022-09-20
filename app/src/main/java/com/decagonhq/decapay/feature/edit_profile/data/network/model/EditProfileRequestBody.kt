package com.decagonhq.decapay.feature.edit_profile.data.network.model

data class EditProfileRequestBody(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    )
