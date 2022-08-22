package com.decagonhq.decapay.feature.listbudgetcategories.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
) : Serializable
