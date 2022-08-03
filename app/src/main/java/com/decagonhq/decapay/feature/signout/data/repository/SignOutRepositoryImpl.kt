package com.decagonhq.decapay.feature.signout.data.repository


import com.decagonhq.decapay.feature.signout.data.network.api.SignOutApi
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutResponse
import com.decagonhq.decapay.feature.signout.domain.repository.SignOutRepository
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import javax.inject.Inject

class SignOutRepositoryImpl @Inject constructor(private val signOutApi: SignOutApi) :
    SignOutRepository {

    override suspend fun signOutUser(signOutRequestBody: SignOutRequestBody): SignOutResponse {
        return signOutApi.signOut("Bearer ${signOutRequestBody.token}", signOutRequestBody)
    }
}
