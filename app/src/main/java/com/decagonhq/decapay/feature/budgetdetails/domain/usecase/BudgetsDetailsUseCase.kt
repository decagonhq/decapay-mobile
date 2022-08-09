package com.decagonhq.decapay.feature.budgetdetails.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.domain.repository.BudgetDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BudgetsDetailsUseCase @Inject constructor(
    private val budgetDetailsRepository: BudgetDetailsRepository,
    private val exceptionHandler: ExceptionHandler
) {


    operator fun invoke(budgetId:Int): Flow<Resource<BudgetDetailsResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                val response = budgetDetailsRepository.getBudgetDetails(budgetId)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                val message = exceptionHandler.parse(e)
                emit(Resource.Error(message))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server check your internet connection"))
            }
        }
}