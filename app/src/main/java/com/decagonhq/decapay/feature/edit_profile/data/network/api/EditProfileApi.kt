package com.decagonhq.decapay.feature.edit_profile.data.network.api

import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface EditProfileApi {

    @PUT("api/v1/profile")
    suspend fun editProfile(
        @Body editProfileRequestBody: EditProfileRequestBody
    ):EditProfileResponse
}