package com.decagonhq.decapay.feature.editlogexpense.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse
import com.decagonhq.decapay.feature.editlogexpense.domain.repository.EditLogExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateLogExpenseUsecase @Inject constructor(
    private val editLogExpenseRepository: EditLogExpenseRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(
        expenseId: Int,
        editLogExpenseRequestBody: EditLogExpenseRequestBody
    ): Flow<Resource<EditLogExpenseResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = editLogExpenseRepository.updateLogExpense(expenseId, editLogExpenseRequestBody)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}