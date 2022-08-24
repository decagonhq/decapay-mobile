package com.decagonhq.decapay.feature.editbudgetlineitem.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse
import com.decagonhq.decapay.feature.editbudgetlineitem.domain.repository.EditBudgetLineItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateBudgetLineItemUsecase @Inject constructor(
    private val editBudgetLineItemRepository: EditBudgetLineItemRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(
        budgetId: Int,
        categoryId: Int,
        editBudgetLineItemRequestBody: EditBudgetLineItemRequestBody
    ): Flow<Resource<EditBudgetLineItemResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = editBudgetLineItemRepository.updateBudgetLineItem(budgetId, categoryId, editBudgetLineItemRequestBody)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}
