package com.decagonhq.decapay.feature.changepassword.domain.repository

import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordRequestBody
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse

interface ChangePasswordRepository {

    suspend fun changePassword(
        changePasswordRequestBody: ChangePasswordRequestBody
    ): ChangePasswordResponse
}
