package com.decagonhq.decapay.feature.budgetdetails.data.repository

import com.decagonhq.decapay.feature.budgetdetails.data.network.api.BudgetDetailsApi
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.domain.repository.BudgetDetailsRepository
import javax.inject.Inject

class BudgetDetailsRepositoryImpl @Inject constructor(
    private val budgetDetailsApi: BudgetDetailsApi
):BudgetDetailsRepository {



    override suspend fun getBudgetDetails(budgetId: Int,token:String): BudgetDetailsResponse {
       return budgetDetailsApi.getBudgetDetails(budgetId,"Bearer $token")

    }


}