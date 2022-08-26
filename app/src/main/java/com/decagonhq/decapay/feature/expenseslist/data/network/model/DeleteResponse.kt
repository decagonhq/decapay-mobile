package com.decagonhq.decapay.feature.expenseslist.data.network.model


import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("debugMessage")
    val debugMessage: String,
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subErrors")
    val subErrors: List<SubErrorX>,
    @SerializedName("timestamp")
    val timestamp: String
)