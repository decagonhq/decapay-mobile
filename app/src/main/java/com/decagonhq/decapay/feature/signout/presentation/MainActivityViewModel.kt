package com.decagonhq.decapay.feature.signout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutResponse
import com.decagonhq.decapay.feature.signout.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: SignOutUseCase,
) : ViewModel() {



    private val _signOutResponse = MutableStateFlow<Resource<SignOutResponse>>(Resource.Loading())
    val signOutResponse: StateFlow<Resource<SignOutResponse>> get() = _signOutResponse

    fun signOutUser(signOutRequestBody: SignOutRequestBody){

        viewModelScope.launch {
            useCase(
                signOutRequestBody
            ).collect {
                _signOutResponse.value = it
            }
        }

    }



}