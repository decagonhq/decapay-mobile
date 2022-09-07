package com.decagonhq.decapay.feature.usersettings.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterRequestBody
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse
import com.decagonhq.decapay.feature.usersettings.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RegisterUsecase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(registerRequestBody: RegisterRequestBody): Flow<Resource<RegisterResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = registerRepository.registerUser(registerRequestBody)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}