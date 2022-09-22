package com.decagonhq.decapay.feature.edit_profile.data.network.model
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface EditProfileApi {

    @PUT("api/v1/profile")
    suspend fun editProfile(
        @Body editProfileRequestBody: EditProfileRequestBody
    ):EditProfileResponse

    @GET("api/v1/profile")
    suspend fun getUserProfile(

    ): UserProfileResponse
}