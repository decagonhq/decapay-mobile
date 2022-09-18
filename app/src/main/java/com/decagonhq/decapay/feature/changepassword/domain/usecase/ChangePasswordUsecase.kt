package com.decagonhq.decapay.feature.changepassword.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordRequestBody
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse
import com.decagonhq.decapay.feature.changepassword.domain.repository.ChangePasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ChangePasswordUsecase @Inject constructor(
    private val changePasswordRepository: ChangePasswordRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(changePasswordRequestBody: ChangePasswordRequestBody): Flow<Resource<ChangePasswordResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = changePasswordRepository.changePassword(changePasswordRequestBody)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}