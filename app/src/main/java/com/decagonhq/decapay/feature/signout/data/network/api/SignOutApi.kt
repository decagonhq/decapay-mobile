package com.decagonhq.decapay.feature.signout.data.network.api


import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutResponse
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignOutApi {

    @POST("api/v1/signout")
    suspend fun signOut(
        @Header("Authorization") authorization:String,
        @Body signOutRequestBody: SignOutRequestBody

    ): SignOutResponse
}
