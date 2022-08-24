package com.decagonhq.decapay.feature.expenseslist.data.repository

import com.decagonhq.decapay.feature.expenseslist.data.network.api.ExpenseListApi
import com.decagonhq.decapay.feature.expenseslist.domain.repository.ExpenseListRepository
import javax.inject.Inject

class ExpenseListRepositoryImpl @Inject constructor(
    private  val expenseListApi: ExpenseListApi
) : ExpenseListRepository {
    override suspend fun getExpenseList(page: Int): Any {
        return  expenseListApi.getBudgetList(page,20)
    }
}