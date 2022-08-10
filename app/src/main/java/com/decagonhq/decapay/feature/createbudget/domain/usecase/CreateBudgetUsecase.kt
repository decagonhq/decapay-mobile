package com.decagonhq.decapay.feature.createbudget.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse
import com.decagonhq.decapay.feature.createbudget.domain.repository.CreateBudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateBudgetUsecase @Inject constructor(
    private val createBudgetRepository: CreateBudgetRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(createBudgetRequestBody: CreateBudgetRequestBody): Flow<Resource<CreateBudgetResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = createBudgetRepository.createBudget(createBudgetRequestBody)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
