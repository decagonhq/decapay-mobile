package com.decagonhq.decapay.feature.usersettings.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetLocalizationReferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetLocalizationReferenceUsecase @Inject constructor(
    private val getLocalizationReferenceRepository: GetLocalizationReferenceRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(): Flow<Resource<GetLocalizationReferenceResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = getLocalizationReferenceRepository.getLocalizationReference()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        } catch (e: Exception) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        }
    }
}