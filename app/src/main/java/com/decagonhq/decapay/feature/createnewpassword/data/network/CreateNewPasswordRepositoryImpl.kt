package com.decagonhq.decapay.feature.createnewpassword.data.network

import com.decagonhq.decapay.feature.createnewpassword.data.network.api.CreateNewPasswordApi
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import com.decagonhq.decapay.feature.createnewpassword.domain.repository.CreateNewPasswordRepository
import javax.inject.Inject

class CreateNewPasswordRepositoryImpl @Inject constructor(
    private val createNewPasswordApi: CreateNewPasswordApi
) : CreateNewPasswordRepository {
    override suspend fun createUserNewPassword(createNewPasswordRequest: CreateNewPasswordRequest): CreateNewPasswordResponse {
        return createNewPasswordApi.createNewPassword(createNewPasswordRequest)
    }
}
