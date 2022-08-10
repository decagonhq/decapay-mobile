package com.decagonhq.decapay.feature.budgetdetails.data.network.model


import com.google.gson.annotations.SerializedName

data class BudgetDetailsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timestamp: String
)