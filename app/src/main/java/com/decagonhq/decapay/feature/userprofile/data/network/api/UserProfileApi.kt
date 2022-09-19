package com.decagonhq.decapay.feature.userprofile.data.network.api

import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import retrofit2.http.GET

interface UserProfileApi {

    @GET("api/v1/user")
    suspend fun getUserProfile(

    ): UserProfileResponse
}