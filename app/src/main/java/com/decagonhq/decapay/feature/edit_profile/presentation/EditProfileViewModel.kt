package com.decagonhq.decapay.feature.edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileRequestBody
import com.decagonhq.decapay.feature.edit_profile.data.network.model.EditProfileResponse
import com.decagonhq.decapay.feature.edit_profile.data.network.model.UserProfileResponse
import com.decagonhq.decapay.feature.edit_profile.domain.usecase.EditProfileUseCase
import com.decagonhq.decapay.feature.edit_profile.domain.usecase.UserProfileUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val userProfileUsecase: UserProfileUsecase

) :ViewModel() {

    private val _editProfileResponse = MutableStateFlow<Resource<EditProfileResponse>>(
        Resource.Loading())
    val editProfileResponse: StateFlow<Resource<EditProfileResponse>> get() = _editProfileResponse

    private val _userProfileResponse = MutableStateFlow<Resource<UserProfileResponse>>(
        Resource.Loading())
    val userProfileResponse: StateFlow<Resource<UserProfileResponse>> get() = _userProfileResponse




    fun  editProfile(editProfileRequestBody: EditProfileRequestBody){
        viewModelScope.launch {

            editProfileUseCase.invoke(editProfileRequestBody).collect {

                _editProfileResponse.value = it
            }
        }

    }

    fun getUserProfile() {
        viewModelScope.launch {

            userProfileUsecase.invoke().collect {

                _userProfileResponse.value = it
            }
        }
    }
}