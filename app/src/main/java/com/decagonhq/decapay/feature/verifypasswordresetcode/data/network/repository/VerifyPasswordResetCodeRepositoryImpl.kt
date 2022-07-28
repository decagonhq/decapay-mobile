package com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.repository

import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.api.VerifyPasswordResetCodeApi
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponseOld
import com.decagonhq.decapay.feature.verifypasswordresetcode.domain.repository.VerifyPasswordResetCodeRepository
import javax.inject.Inject

class VerifyPasswordResetCodeRepositoryImpl @Inject constructor(
    private val verifyPasswordResetCodeApi: VerifyPasswordResetCodeApi
) : VerifyPasswordResetCodeRepository {
    override suspend fun verifyPasswordResetCode(verifyPasswordResetCodeRequest: VerifyPasswordResetCodeRequest): VerifyPasswordResetCodeResponse {
        return verifyPasswordResetCodeApi.verifyPasswordResetCode(NetworkConstant.REQUEST_HEADER_KEY, verifyPasswordResetCodeRequest)
    }
}