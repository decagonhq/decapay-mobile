package com.decagonhq.decapay.feature.userprofile.data.repository

import com.decagonhq.decapay.feature.userprofile.data.network.api.UserProfileApi
import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import com.decagonhq.decapay.feature.userprofile.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileApi: UserProfileApi
) : UserProfileRepository {

    override suspend fun getUseProfile(): UserProfileResponse {
        return userProfileApi.getUserProfile()
    }
}