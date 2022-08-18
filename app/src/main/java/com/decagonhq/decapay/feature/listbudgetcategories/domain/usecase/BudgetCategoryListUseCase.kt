package com.decagonhq.decapay.feature.listbudgetcategories.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudgetcategories.domain.repository.BudgetCategoryListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BudgetCategoryListUseCase @Inject constructor(
    private val budgetCategoryListRepository: BudgetCategoryListRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(): Flow<Resource<Any>> = flow {

        try {
            emit(Resource.Loading())
            val response = budgetCategoryListRepository.getBudgetCategories()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }


}