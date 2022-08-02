package com.decagonhq.decapay.common.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("errors")
    val errors: HashMap<String, Any>?
)
