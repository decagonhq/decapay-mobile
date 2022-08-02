package com.decagonhq.decapay.feature.signup.data.network.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastLogin")
    val lastLogin: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("userStatus")
    val userStatus: String?
)