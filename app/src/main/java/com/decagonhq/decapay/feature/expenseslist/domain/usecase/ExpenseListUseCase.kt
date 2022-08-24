package com.decagonhq.decapay.feature.expenseslist.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.domain.repository.ExpenseListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ExpenseListUseCase @Inject constructor(
    private val expenseListRepository: ExpenseListRepository,
    private val exceptionHandler: ExceptionHandler
){
    operator fun invoke(page: Int): Flow<Resource<Any>> = flow {

        try {
            emit(Resource.Loading())
            val response = expenseListRepository.getExpenseList(page)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }


    fun getNextPage(page: Int): Flow<Resource<Any>> = flow {
        try {
            val response = expenseListRepository.getExpenseList(page)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }


}