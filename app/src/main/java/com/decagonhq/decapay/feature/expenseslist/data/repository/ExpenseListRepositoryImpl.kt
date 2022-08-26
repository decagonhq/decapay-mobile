package com.decagonhq.decapay.feature.expenseslist.data.repository

import com.decagonhq.decapay.feature.expenseslist.data.network.api.ExpenseListApi
import com.decagonhq.decapay.feature.expenseslist.data.network.model.DeleteResponse
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse
import com.decagonhq.decapay.feature.expenseslist.domain.repository.ExpenseListRepository
import javax.inject.Inject

class ExpenseListRepositoryImpl @Inject constructor(
    private  val expenseListApi: ExpenseListApi
) : ExpenseListRepository {
    override suspend fun getExpenseList(budgetId: Int,categoryId: Int,page: Int): ExpenseListResponse {
        return  expenseListApi.getBudgetList(budgetId,categoryId,page,20)
    }

    override suspend fun deleteExpense(expenseId: Int) {
         expenseListApi.deleteExpense(expenseId)
    }
}