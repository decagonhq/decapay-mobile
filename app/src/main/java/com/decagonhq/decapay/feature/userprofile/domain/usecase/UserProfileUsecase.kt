package com.decagonhq.decapay.feature.userprofile.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import com.decagonhq.decapay.feature.listbudgetcategories.domain.repository.BudgetCategoryListRepository
import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import com.decagonhq.decapay.feature.userprofile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserProfileUsecase @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(): Flow<Resource<UserProfileResponse>> = flow {

        try {
            emit(Resource.Loading())
            val response = userProfileRepository.getUseProfile()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = exceptionHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}