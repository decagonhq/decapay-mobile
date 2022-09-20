package com.decagonhq.decapay.feature.edit_profile.domain.repository

import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileResponse

interface EditProfileRepository {

    suspend fun editProfile(editProfileRequestBody: EditProfileRequestBody):EditProfileResponse
}