package com.decagonhq.decapay.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Content(
    @SerializedName("displayPercentageSpentSoFar")
    val displayPercentageSpentSoFar: String,
    @SerializedName("displayProjectedAmount")
    val displayProjectedAmount: String,
    @SerializedName("displayTotalAmountSpentSoFar")
    val displayTotalAmountSpentSoFar: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("percentageSpentSoFar")
    val percentageSpentSoFar: Double,
    @SerializedName("period")
    val period: String,
    @SerializedName("projectedAmount")
    val projectedAmount: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("totalAmountSpentSoFar")
    val totalAmountSpentSoFar: Double
)
