package com.decagonhq.decapay.feature.verifypasswordresetcode.domain.verifypasswordresetcodeusecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import com.decagonhq.decapay.feature.verifypasswordresetcode.domain.repository.VerifyPasswordResetCodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VerifyPasswordResetCodeUsecase @Inject constructor(
    private val verifyPasswordResetCodeRepository: VerifyPasswordResetCodeRepository,
    private val exceptionHandler: ExceptionHandler
    ) {

    operator fun invoke(verifyPasswordResetCodeRequest: VerifyPasswordResetCodeRequest): Flow<Resource<VerifyPasswordResetCodeResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = verifyPasswordResetCodeRepository.verifyPasswordResetCode(verifyPasswordResetCodeRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
