package com.decagonhq.decapay.feature.logexpense.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBodyDemo
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo
import com.decagonhq.decapay.feature.logexpense.domain.repository.LogExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AddExpenseUsecase @Inject constructor(
    private val logExpenseRepository: LogExpenseRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(logExpenseRequestBodyDemo: LogExpenseRequestBodyDemo): Flow<Resource<LogExpenseResponseDemo>> = flow {
        try {
            emit(Resource.Loading())
            val response = logExpenseRepository.addExpense(logExpenseRequestBodyDemo)
            emit(Resource.Success(response))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}