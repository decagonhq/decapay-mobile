package com.decagonhq.decapay.feature.signout.data.network.model


import com.google.gson.annotations.SerializedName

data class SignOutResponse(
    val message: String,
    val status: String,
    val timestamp: String
)