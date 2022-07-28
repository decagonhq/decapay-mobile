package com.decagonhq.decapay.feature.signup.domain.usecase

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpError
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import com.decagonhq.decapay.feature.signup.domain.repository.SignUpRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject


class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {

     operator fun invoke(registerBody: SignUpRequestBody): Flow<Resource<SignUpResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.signUpUser(registerBody)
            if(response.isSuccessful){
             val registerResponse =   response.body()!!
                emit(Resource.Success(registerResponse))
            }else{
                val responseError = Gson().fromJson(
                    response.errorBody()?.string() ?: "",
                    SignUpError::class.java
                )
                emit(Resource.Error(responseError.status?:"An unexpected error occurred"))
            }


        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
