package com.decagonhq.decapay.feature.signup.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import com.decagonhq.decapay.feature.signup.domain.usecase.SignUpUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCase: SignUpUseCase,
) : ViewModel() {

    private val _signUpResponse = MutableStateFlow<Resource<SignUpResponse>>(Resource.Loading())
    val registerResponse: StateFlow<Resource<SignUpResponse>> get() = _signUpResponse


    fun signUp(
       signUpRequestBody: SignUpRequestBody
    ) {


        viewModelScope.launch {
            useCase(
              signUpRequestBody
            ).collect {
                _signUpResponse.value = it
            }
        }

    }


}