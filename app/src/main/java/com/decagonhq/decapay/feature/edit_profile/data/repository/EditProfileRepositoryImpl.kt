package com.decagonhq.decapay.feature.edit_profile.data.repository

import com.decagonhq.decapay.feature.edit_profile.data.network.api.EditProfileApi
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileResponse
import com.decagonhq.decapay.feature.edit_profile.domain.repository.EditProfileRepository
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(private  val editProfileApi: EditProfileApi): EditProfileRepository {
    override suspend fun editProfile(editProfileRequestBody: EditProfileRequestBody): EditProfileResponse {
        return  editProfileApi.editProfile(editProfileRequestBody)
    }
}