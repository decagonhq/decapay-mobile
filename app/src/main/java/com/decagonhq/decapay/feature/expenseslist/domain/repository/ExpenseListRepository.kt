package com.decagonhq.decapay.feature.expenseslist.domain.repository


interface ExpenseListRepository {

    suspend fun  getExpenseList(page:Int): Any
}