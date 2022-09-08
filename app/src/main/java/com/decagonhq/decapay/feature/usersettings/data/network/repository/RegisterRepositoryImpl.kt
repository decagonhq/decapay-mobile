package com.decagonhq.decapay.feature.usersettings.data.network.repository

import com.decagonhq.decapay.feature.usersettings.data.network.api.RegisterApi
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterRequestBody
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse
import com.decagonhq.decapay.feature.usersettings.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerApi: RegisterApi
) : RegisterRepository{
    override suspend fun registerUser(registerRequestBody: RegisterRequestBody): RegisterResponse {
        return registerApi.registerUser(registerRequestBody)
    }
}