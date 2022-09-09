package com.decagonhq.decapay.common.data.model.errormodel

data class SubError(
    val `field`: String?,
    val message: String?,
    val `object`: String?,
    val rejectedValue: String?
)