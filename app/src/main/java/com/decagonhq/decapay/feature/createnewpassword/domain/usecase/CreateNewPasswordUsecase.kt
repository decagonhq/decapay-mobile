package com.decagonhq.decapay.feature.createnewpassword.domain.usecase

import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import com.decagonhq.decapay.feature.createnewpassword.domain.repository.CreateNewPasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateNewPasswordUsecase @Inject constructor(private val createNewPasswordRepository: CreateNewPasswordRepository) {
    operator fun invoke(createNewPasswordRequest: CreateNewPasswordRequest): Flow<Resource<CreateNewPasswordResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = createNewPasswordRepository.createUserNewPassword(createNewPasswordRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
