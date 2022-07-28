package com.decagonhq.decapay.feature.createnewpassword.data.network.repository

import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.feature.createnewpassword.data.network.api.CreateNewPasswordApi
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import com.decagonhq.decapay.feature.createnewpassword.domain.repository.CreateNewPasswordRepository
import javax.inject.Inject

class CreateNewPasswordRepositoryImpl @Inject constructor(
    private val createNewPasswordApi: CreateNewPasswordApi
) : CreateNewPasswordRepository {
    override suspend fun createUserNewPassword(createNewPasswordRequest: CreateNewPasswordRequest): CreateNewPasswordResponse {
        return createNewPasswordApi.createNewPassword(NetworkConstant.CREATE_NEW_PASSWORD_HEADER_KEY, createNewPasswordRequest)
    }
}
