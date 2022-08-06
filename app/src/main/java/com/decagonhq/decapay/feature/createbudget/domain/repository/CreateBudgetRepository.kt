package com.decagonhq.decapay.feature.createbudget.domain.repository

interface CreateBudgetRepository {
    suspend fun createBudget()
}