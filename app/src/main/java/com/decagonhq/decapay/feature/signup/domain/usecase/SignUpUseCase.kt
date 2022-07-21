package com.decagonhq.decapay.feature.signup.domain.usecase

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import com.decagonhq.decapay.feature.signup.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject


class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {

     operator fun invoke(registerBody: SignUpRequestBody): Flow<Resource<SignUpResponse>> = flow {
        try {
            emit(Resource.Loading())
            val registerResponse = repository.signUpUser(registerBody)
            emit(Resource.Success(registerResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
