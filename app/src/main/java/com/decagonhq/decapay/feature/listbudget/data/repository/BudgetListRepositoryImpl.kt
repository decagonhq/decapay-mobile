package com.decagonhq.decapay.feature.listbudget.data.repository

import com.decagonhq.decapay.feature.listbudget.data.network.api.BudgetListApi
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import com.decagonhq.decapay.feature.listbudget.domain.repository.BudgetListRepository
import javax.inject.Inject

class BudgetListRepositoryImpl @Inject constructor(
  private  val budgetListApi: BudgetListApi
) : BudgetListRepository {



    override suspend fun getBudgetList(page:Int): BudgetListResponse {
        return  budgetListApi.getBudgetList(page,20)
    }
}