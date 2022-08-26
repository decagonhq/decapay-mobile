package com.decagonhq.decapay.feature.expenseslist.domain.repository

import com.decagonhq.decapay.feature.expenseslist.data.network.model.DeleteResponse
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse


interface ExpenseListRepository {

    suspend fun  getExpenseList(budgetId: Int,categoryId: Int,page:Int): ExpenseListResponse

    suspend fun deleteExpense(expenseId:Int)
}