package com.decagonhq.decapay.feature.expenseslist.data.network.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExpenseContent(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("displayAmount")
    val displayAmount: String,
    @SerializedName("displayTransactionDate")
    val displayTransactionDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("transactionDate")
    val transactionDate: String
): Serializable