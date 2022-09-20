package com.decagonhq.decapay.feature.listbudget.data.network.model

import com.google.gson.annotations.SerializedName

data class BudgetListResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("debugMessage")
    val debugMessage: String,
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subErrors")
    val subErrors: List<SubError>,
    @SerializedName("timestamp")
    val timestamp: String
)
