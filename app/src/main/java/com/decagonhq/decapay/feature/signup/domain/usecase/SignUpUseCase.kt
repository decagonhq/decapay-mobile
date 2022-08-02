package com.decagonhq.decapay.feature.signup.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpError
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import com.decagonhq.decapay.feature.signup.domain.repository.SignUpRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: SignUpRepository,
    private val exceptionHandler: ExceptionHandler
    ) {

    operator fun invoke(registerBody: SignUpRequestBody): Flow<Resource<SignUpResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.signUpUser(registerBody)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
