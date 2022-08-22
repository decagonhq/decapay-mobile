package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.Data

data class CreateBudgetLineItemResponse(
    val `data`: Data?,
    val message: String?,
    val status: String?,
    val timestamp: String?
)