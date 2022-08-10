package com.decagonhq.decapay.feature.budgetdetails.domain.repository

import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse

interface BudgetDetailsRepository {

    suspend fun getBudgetDetails(budgetId: Int):BudgetDetailsResponse
}