package com.decagonhq.decapay.feature.edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileResponse
import com.decagonhq.decapay.feature.edit_profile.domain.usecase.EditProfileUseCase
import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase
) :ViewModel() {

    private val _editProfileResponse = MutableStateFlow<Resource<EditProfileResponse>>(
        Resource.Loading())
    val editProfileResponse: StateFlow<Resource<EditProfileResponse>> get() = _editProfileResponse




    fun  editProfile(editProfileRequestBody: EditProfileRequestBody){
        viewModelScope.launch {

            editProfileUseCase.invoke(editProfileRequestBody).collect {

                _editProfileResponse.value = it
            }
        }

    }
}