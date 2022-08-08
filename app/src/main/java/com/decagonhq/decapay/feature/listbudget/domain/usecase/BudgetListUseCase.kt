package com.decagonhq.decapay.feature.listbudget.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import com.decagonhq.decapay.feature.listbudget.domain.repository.BudgetListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BudgetListUseCase  @Inject constructor(
    private  val budgetListRepository: BudgetListRepository,
    private val exceptionHandler: ExceptionHandler
){
    operator fun invoke(token:String,page:Int): Flow<Resource<BudgetListResponse>> = flow {

        try {
            emit(Resource.Loading())
            val response = budgetListRepository.getBudgetList(token,page)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }


     fun getNextPage(token:String,page:Int): Flow<Resource<BudgetListResponse>> = flow {

        try {
            val response = budgetListRepository.getBudgetList(token, page )
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }

}