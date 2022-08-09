package com.decagonhq.decapay.feature.budgetdetails.data.network.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("budgetPeriod")
    val budgetPeriod: String,
    @SerializedName("displayEndDate")
    val displayEndDate: String,
    @SerializedName("displayPercentageSpentSoFar")
    val displayPercentageSpentSoFar: String,
    @SerializedName("displayProjectedAmount")
    val displayProjectedAmount: String,
    @SerializedName("displayStartDate")
    val displayStartDate: String,
    @SerializedName("displayTotalAmountSpentSoFar")
    val displayTotalAmountSpentSoFar: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lineItems")
    val lineItems: List<Any>,
    @SerializedName("percentageSpentSoFar")
    val percentageSpentSoFar: Double,
    @SerializedName("projectedAmount")
    val projectedAmount: Double,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("title")
    val title: String
)