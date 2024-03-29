package com.decagonhq.decapay.feature.listbudget.domain.repository

import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse

interface BudgetListRepository {

    suspend fun getBudgetList(page: Int, state: String): BudgetListResponse
}
