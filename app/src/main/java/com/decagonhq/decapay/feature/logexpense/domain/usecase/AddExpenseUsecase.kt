package com.decagonhq.decapay.feature.logexpense.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponse
import com.decagonhq.decapay.feature.logexpense.domain.repository.LogExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AddExpenseUsecase @Inject constructor(
    private val logExpenseRepository: LogExpenseRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(budgetId: Int, categoryId: Int, logExpenseRequestBody: LogExpenseRequestBody): Flow<Resource<LogExpenseResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = logExpenseRepository.addExpense(budgetId, categoryId, logExpenseRequestBody)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}
