package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model

data class GetBudgetCategoryListResponse(
    val `data`: List<CategoryItem?>?,
    val message: String?,
    val status: String?,
    val timestamp: String?
)