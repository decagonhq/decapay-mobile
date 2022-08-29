package com.decagonhq.decapay.feature.editlogexpense.data.network.repository

import com.decagonhq.decapay.feature.editlogexpense.data.network.api.EditLogExpenseApi
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse
import com.decagonhq.decapay.feature.editlogexpense.domain.EditLogExpenseRepository
import javax.inject.Inject

class EditLogExpenseRepositoryImpl @Inject constructor(
    private val editLogExpenseApi: EditLogExpenseApi
) : EditLogExpenseRepository {
    override suspend fun updateLogExpense(
        expenseId: Int,
        editLogExpenseRequestBody: EditLogExpenseRequestBody
    ): EditLogExpenseResponse {
        return editLogExpenseApi.updateLogExpense(expenseId, editLogExpenseRequestBody)
    }
}