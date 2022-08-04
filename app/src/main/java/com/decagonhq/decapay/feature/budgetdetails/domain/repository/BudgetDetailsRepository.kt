package com.decagonhq.decapay.feature.budgetdetails.domain.repository

interface BudgetDetailsRepository {

    suspend fun getBudgetDetails():Any
}