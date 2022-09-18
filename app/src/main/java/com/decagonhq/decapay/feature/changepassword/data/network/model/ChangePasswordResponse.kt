package com.decagonhq.decapay.feature.changepassword.data.network.model

import com.decagonhq.decapay.feature.createbudget.data.network.model.Data

data class ChangePasswordResponse(
    val `data`: Data?,
    val message: String?,
    val status: String?,
    val timestamp: String?
)
