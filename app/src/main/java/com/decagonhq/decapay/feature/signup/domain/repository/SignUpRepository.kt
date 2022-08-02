package com.decagonhq.decapay.feature.signup.domain.repository

import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse

interface SignUpRepository {

    suspend fun signUpUser(signUpRequest: SignUpRequestBody): SignUpResponse
}
