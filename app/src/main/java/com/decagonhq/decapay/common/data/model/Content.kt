package com.decagonhq.decapay.common.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Content (
    @SerializedName("displayPercentageSpentSoFar")
    val displayPercentageSpentSoFar: String,
    @SerializedName("displayProjectedAmount")
    val displayProjectedAmount: String,
    @SerializedName("displayTotalAmountSpentSoFar")
    val displayTotalAmountSpentSoFar: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("percentageSpentSoFar")
    val percentageSpentSoFar: Int,
    @SerializedName("period")
    val period: String,
    @SerializedName("projectedAmount")
    val projectedAmount: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("totalAmountSpentSoFar")
    val totalAmountSpentSoFar: Int
): Serializable