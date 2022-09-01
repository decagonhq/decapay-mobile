package com.decagonhq.decapay.feature.editbudgetcategory.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse
import com.decagonhq.decapay.feature.editbudgetcategory.domain.repository.EditBudgetCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateBudgetCategoryUsecase @Inject constructor(
    private val editBudgetCategoryRepository: EditBudgetCategoryRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(categoryId: Int, editBudgetCategoryRequestBody: EditBudgetCategoryRequestBody): Flow<Resource<EditBudgetCategoryResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = editBudgetCategoryRepository.updateBudgetCategory(categoryId, editBudgetCategoryRequestBody)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}