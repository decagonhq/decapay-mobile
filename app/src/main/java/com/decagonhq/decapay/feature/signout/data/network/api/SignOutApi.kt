package com.decagonhq.decapay.feature.signout.data.network.api

import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignOutApi {

    @POST("api/v1/signout")
    suspend fun signOut(
        @Body signOutRequestBody: SignOutRequestBody
    ): SignOutResponse
}
