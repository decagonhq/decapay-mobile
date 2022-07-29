package com.decagonhq.decapay.feature.signup.domain.repository

import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import retrofit2.Response

interface SignUpRepository {

    suspend fun signUpUser(signUpRequest: SignUpRequestBody): Response<SignUpResponse>
}
