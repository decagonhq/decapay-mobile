package com.decagonhq.decapay.common.data.model.errormodel

import com.google.gson.annotations.SerializedName

data class ErrorSent(

    @SerializedName("errorCode")
    val errorCode: String?,
    @SerializedName("message")
    val message: Any?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("subErrors")
    val subErrors: List<SubError?>?

)
