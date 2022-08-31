package com.decagonhq.decapay.feature.editlogexpense.domain.repository

import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse

interface EditLogExpenseRepository {

    suspend fun updateLogExpense(
        expenseId: Int,
        editLogExpenseRequestBody: EditLogExpenseRequestBody
    ): EditLogExpenseResponse
}
