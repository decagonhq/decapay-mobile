package com.decagonhq.decapay.feature.editbudget.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.repository.FetchUserBudgetToEditRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchBudgetUsecase @Inject constructor(
    private val fetchUserBudgetToEditRepository: FetchUserBudgetToEditRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(budgetId: Int): Flow<Resource<FetchEditBudgetResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = fetchUserBudgetToEditRepository.fetchBudgetToEdit(budgetId)
            emit(Resource.Success(response))
        } catch (e: HttpException){
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}