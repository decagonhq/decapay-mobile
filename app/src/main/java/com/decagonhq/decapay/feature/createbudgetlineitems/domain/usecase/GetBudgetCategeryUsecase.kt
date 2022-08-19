package com.decagonhq.decapay.feature.createbudgetlineitems.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository.GetBudgetCategoryListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBudgetCategeryUsecase @Inject constructor(
    private val getBudgetCategoryListRepository: GetBudgetCategoryListRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(): Flow<Resource<GetBudgetCategoryListResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = getBudgetCategoryListRepository.getBudgetCategoryList()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }

}