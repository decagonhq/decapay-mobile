package com.decagonhq.decapay.feature.login.data.network.repository

import com.decagonhq.decapay.feature.login.data.network.api.LoginApi
import com.decagonhq.decapay.feature.login.data.network.model.LoginRequestBody
import com.decagonhq.decapay.feature.login.data.network.model.LoginResponse
import com.decagonhq.decapay.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun loginAUser(loginRequestBody: LoginRequestBody): LoginResponse {
        return loginApi.loginUser(loginRequestBody)
    }
}
