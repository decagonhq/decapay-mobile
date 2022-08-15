package com.decagonhq.decapay.feature.editbudget.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetRequestBody
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.repository.UpdateEditBudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateEditBudgetUsecase @Inject constructor(
    private val updateEditBudgetRepository: UpdateEditBudgetRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(updateBudgetRequestBody: UpdateBudgetRequestBody, budgetInt: Int): Flow<Resource<UpdateBudgetResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = updateEditBudgetRepository.updateBudget(updateBudgetRequestBody, budgetInt)
            emit(Resource.Success(response))
        } catch (e: HttpException){
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}