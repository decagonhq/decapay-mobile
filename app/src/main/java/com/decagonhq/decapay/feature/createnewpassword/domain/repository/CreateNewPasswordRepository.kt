package com.decagonhq.decapay.feature.createnewpassword.domain.repository

import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse

interface CreateNewPasswordRepository {
    suspend fun createUserNewPassword(
        createNewPasswordRequest: CreateNewPasswordRequest
    ): CreateNewPasswordResponse
}
