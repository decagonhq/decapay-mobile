package com.decagonhq.decapay.feature.signout.domain.repository

import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutResponse


interface SignOutRepository {

    suspend fun signOutUser(signOutRequestBody: SignOutRequestBody): SignOutResponse
}
