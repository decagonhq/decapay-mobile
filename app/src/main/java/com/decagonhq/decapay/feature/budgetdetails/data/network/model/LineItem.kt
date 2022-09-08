package com.decagonhq.decapay.feature.budgetdetails.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LineItem(
    @SerializedName("budgetId")
    val budgetId: Int?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("displayPercentageSpentSoFar")
    val displayPercentageSpentSoFar: String?,
    @SerializedName("displayProjectedAmount")
    val displayProjectedAmount: String?,
    @SerializedName("displayTotalAmountSpentSoFar")
    val displayTotalAmountSpentSoFar: String?,
    @SerializedName("percentageSpentSoFar")
    val percentageSpentSoFar: Int?,
    @SerializedName("projectedAmount")
    val projectedAmount: Int?,
    @SerializedName("totalAmountSpentSoFar")
    val totalAmountSpentSoFar: Int?
) : Serializable
