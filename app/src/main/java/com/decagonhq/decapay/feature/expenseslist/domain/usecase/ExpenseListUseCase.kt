package com.decagonhq.decapay.feature.expenseslist.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.data.network.model.DeleteResponse
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse
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
    operator fun invoke(budgetId: Int,categoryId: Int,page: Int): Flow<Resource<ExpenseListResponse>> = flow {

        try {
            emit(Resource.Loading())
            val response = expenseListRepository.getExpenseList(budgetId,categoryId,page)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }


    fun getNextPage(budgetId: Int,categoryId: Int,page: Int): Flow<Resource<ExpenseListResponse>> = flow {
        try {
            val response = expenseListRepository.getExpenseList(budgetId,categoryId,page)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }


    suspend fun deleteExpense(expenseId: Int) : Flow<Resource<Any>> =
        flow {
            try {
             val response =   expenseListRepository.deleteExpense(expenseId)
                emit(Resource.Success(""))
            } catch (e: HttpException) {
                val message = exceptionHandler.parse(e)
                emit(Resource.Error(message))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server check your internet connection"))
            }
        }


}