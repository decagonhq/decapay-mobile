package com.decagonhq.decapay.feature.userprofile.domain.repository

import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse

interface UserProfileRepository {

    suspend fun  getUseProfile(): UserProfileResponse


}