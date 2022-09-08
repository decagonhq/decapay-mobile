package com.decagonhq.decapay.feature.usersettings.domain.repository

import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterRequestBody
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse

interface RegisterRepository {
    suspend fun registerUser(
        registerRequestBody: RegisterRequestBody
    ): RegisterResponse
}