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

        }catch (e: HttpException){

        } catch (e: IOException){

        }
    }

}