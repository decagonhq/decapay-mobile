package com.decagonhq.decapay.feature.budgetdetails.data.repository

import com.decagonhq.decapay.feature.budgetdetails.data.api.BudgetDetailApi
import com.decagonhq.decapay.feature.budgetdetails.domain.repository.BudgetDetailsRepository
import javax.inject.Inject

class BudgetDetailsRepositoryImpl @Inject constructor(
    private val budgetDetailApi: BudgetDetailApi
):BudgetDetailsRepository {



    override suspend fun getBudgetDetails(): Any {
       return budgetDetailApi.getBudgetDetails()
    }


}