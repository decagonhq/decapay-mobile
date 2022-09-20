package com.decagonhq.decapay.feature.edit_profile.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileResponse
import com.decagonhq.decapay.feature.edit_profile.domain.repository.EditProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: EditProfileRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(editProfileRequestBody: EditProfileRequestBody): Flow<Resource<EditProfileResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.editProfile(editProfileRequestBody)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}