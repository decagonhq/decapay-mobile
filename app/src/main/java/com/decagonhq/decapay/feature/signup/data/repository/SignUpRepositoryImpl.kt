package com.decagonhq.decapay.feature.signup.data.repository;


import com.decagonhq.decapay.feature.signup.data.network.api.SignUpApi
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import javax.inject.Inject
import com.decagonhq.decapay.feature.signup.domain.repository.SignUpRepository
import retrofit2.Response

class SignUpRepositoryImpl @Inject constructor(private val signUpApi: SignUpApi) : SignUpRepository{

    override suspend fun signUpUser(signUpRequest: SignUpRequestBody): Response<SignUpResponse> {
      return  signUpApi.signUp(signUpRequest)
    }
}
