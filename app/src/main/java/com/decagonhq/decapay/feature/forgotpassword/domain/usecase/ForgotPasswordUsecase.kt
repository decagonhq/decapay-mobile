package com.decagonhq.decapay.feature.forgotpassword.domain.usecase

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import com.decagonhq.decapay.feature.forgotpassword.domain.repository.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ForgotPasswordUsecase @Inject constructor(private val forgotPasswordRepository: ForgotPasswordRepository) {

    operator fun invoke(forgotPasswordRequest: ForgotPasswordRequest): Flow<Resource<ForgotPasswordResponse>> = flow {
        try {
            emit(Resource.Loading())
            val forgotPasswordResponse = forgotPasswordRepository.forgotPassword(forgotPasswordRequest)
            emit(Resource.Success(forgotPasswordResponse))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }

}