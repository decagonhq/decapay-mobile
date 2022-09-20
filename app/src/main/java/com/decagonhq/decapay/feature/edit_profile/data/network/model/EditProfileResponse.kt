package com.decagonhq.decapay.feature.edit_profile.data.network.model


import com.google.gson.annotations.SerializedName

data class EditProfileResponse(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("debugMessage")
    val debugMessage: Any,
    @SerializedName("errorCode")
    val errorCode: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subErrors")
    val subErrors: Any,
    @SerializedName("timestamp")
    val timestamp: String
)