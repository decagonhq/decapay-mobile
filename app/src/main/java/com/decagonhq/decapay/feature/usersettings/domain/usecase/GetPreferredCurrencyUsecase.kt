package com.decagonhq.decapay.feature.usersettings.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredCurrencyResponseDemo
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetPreferredCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPreferredCurrencyUsecase @Inject constructor(
    private val getPreferredCurrencyRepository: GetPreferredCurrencyRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(): Flow<Resource<GetPreferredCurrencyResponseDemo>> = flow {
        try {
            emit(Resource.Loading())
            val response = getPreferredCurrencyRepository.getPreferredCurrency()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}