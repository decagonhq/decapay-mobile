package com.decagonhq.decapay.feature.createbudgetlineitems.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository.CreateBudgetLineItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreateBudgetLineItemUsecase @Inject constructor(
    private val createBudgetLineItemRepository: CreateBudgetLineItemRepository,
    private val exceptionHandler: ExceptionHandler
)  {
    operator fun invoke(budgetId: Int, createBudgetLineItemRequestBody: CreateBudgetLineItemRequestBody): Flow<Resource<CreateBudgetLineItemResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = createBudgetLineItemRepository.createBudgetLineItem(budgetId, createBudgetLineItemRequestBody)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}