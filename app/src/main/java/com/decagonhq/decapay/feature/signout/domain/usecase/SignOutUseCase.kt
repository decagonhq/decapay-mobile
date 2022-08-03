package com.decagonhq.decapay.feature.signout.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutResponse
import com.decagonhq.decapay.feature.signout.domain.repository.SignOutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: SignOutRepository,
    private val exceptionHandler: ExceptionHandler
) {

    operator fun invoke(signOutRequestBody: SignOutRequestBody): Flow<Resource<SignOutResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.signOutUser(signOutRequestBody)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}
