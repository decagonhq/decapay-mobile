package com.decagonhq.decapay.feature.userprofile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import com.decagonhq.decapay.feature.userprofile.domain.usecase.UserProfileUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileUsecase: UserProfileUsecase
): ViewModel() {

    private val _userProfileResponse = MutableStateFlow<Resource<UserProfileResponse>>(
        Resource.Loading())
    val userProfileResponse: StateFlow<Resource<UserProfileResponse>> get() = _userProfileResponse


    fun getUserProfile() {
        viewModelScope.launch {

            userProfileUsecase.invoke().collect {

                _userProfileResponse.value = it
            }
        }
    }

}