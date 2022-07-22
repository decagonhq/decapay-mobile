package com.decagonhq.decapay.feature.login.domain.repository

import com.decagonhq.decapay.feature.login.data.network.model.LoginRequestBody
import com.decagonhq.decapay.feature.login.data.network.model.LoginResponse

interface LoginRepository {

    suspend fun loginAUser(loginRequestBody: LoginRequestBody): LoginResponse

}