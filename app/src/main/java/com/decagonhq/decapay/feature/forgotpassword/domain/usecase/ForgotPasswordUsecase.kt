package com.decagonhq.decapay.feature.forgotpassword.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import com.decagonhq.decapay.feature.forgotpassword.domain.repository.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ForgotPasswordUsecase @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordRepository,
    private val exceptionHandler: ExceptionHandler
    ) {

    operator fun invoke(forgotPasswordRequest: ForgotPasswordRequest): Flow<Resource<ForgotPasswordResponse>> = flow {
        try {
            emit(Resource.Loading())
            val forgotPasswordResponse = forgotPasswordRepository.forgotPassword(forgotPasswordRequest)
            emit(Resource.Success(forgotPasswordResponse))
        }catch (e: HttpException){
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }

}