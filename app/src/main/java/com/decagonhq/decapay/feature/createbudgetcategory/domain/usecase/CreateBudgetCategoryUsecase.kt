package com.decagonhq.decapay.feature.createbudgetcategory.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse
import com.decagonhq.decapay.feature.createbudgetcategory.domain.repository.CreateBudgetCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateBudgetCategoryUsecase @Inject constructor(
    private val createBudgetCategoryRepository: CreateBudgetCategoryRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(createBudgetCategoryRequestBody: CreateBudgetCategoryRequestBody): Flow<Resource<CreateBudgetCategoryResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = createBudgetCategoryRepository.createBudgetCategoryRepository(createBudgetCategoryRequestBody)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
