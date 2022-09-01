package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.api

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse
import retrofit2.http.GET

interface GetBudgetCategoryListApi {

    @GET("api/v1/budget_categories")
    suspend fun getBudgetCategoryList(): GetBudgetCategoryListResponse
}
