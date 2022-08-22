package com.decagonhq.decapay.feature.budgetdetails.domain.repository

import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.DeleteLineItemResponse

interface BudgetDetailsRepository {

    suspend fun getBudgetDetails(budgetId: Int):BudgetDetailsResponse

    suspend fun deleteLineItem(budgetId: Int,categoryId:Int)
}