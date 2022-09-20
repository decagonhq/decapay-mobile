package com.decagonhq.decapay.feature.changepassword.data.network.repository

import com.decagonhq.decapay.feature.changepassword.data.network.api.ChangePasswordApi
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordRequestBody
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse
import com.decagonhq.decapay.feature.changepassword.domain.repository.ChangePasswordRepository
import javax.inject.Inject

class ChangePasswordRepositoryImpl @Inject constructor(
    private val changePasswordApi: ChangePasswordApi
): ChangePasswordRepository {
    override suspend fun changePassword(changePasswordRequestBody: ChangePasswordRequestBody): ChangePasswordResponse {
        return changePasswordApi.changePassword(changePasswordRequestBody)
    }
}