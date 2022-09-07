package com.decagonhq.decapay.feature.budgetdetails.data.network.model.bundle

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LogExpenseData(
    @SerializedName("budgetId")
    var budgetId: Int?,

    @SerializedName("category")
    var category: String?,

    @SerializedName("categoryId")
    var categoryId: Int?,

    var calendarSelectedDate: String?,
    var startDateCaptured: Long?,
    var endDateCaptured: Long?
) : Serializable
