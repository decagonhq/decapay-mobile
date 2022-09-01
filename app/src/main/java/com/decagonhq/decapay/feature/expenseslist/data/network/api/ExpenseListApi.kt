package com.decagonhq.decapay.feature.expenseslist.data.network.api

import com.decagonhq.decapay.feature.expenseslist.data.network.model.DeleteResponse
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExpenseListApi {

    @GET("api/v1/budgets/{budgetId}/lineItems/{categoryId}/expenses")
    suspend fun getExpenseList(
        @Path("budgetId") budgetId: Int,
        @Path("categoryId") categoryId : Int,
        @Query("page") address: Int,
        @Query("size") size: Int,
    ): ExpenseListResponse


    @DELETE("api/v1/expenses/{expenseId}")
    suspend fun deleteExpense(
    @Path("expenseId")   expenseId:Int
    ): Response<Void>
}