package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterRequestBody
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse
import com.decagonhq.decapay.feature.usersettings.domain.usecase.RegisterUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _registerResponse = MutableSharedFlow<Resource<RegisterResponse>>()
    val registerResponse: SharedFlow<Resource<RegisterResponse>> = _registerResponse.asSharedFlow()

    fun registerUser(registerRequestBody: RegisterRequestBody) {
        viewModelScope.launch {
            registerUsecase(registerRequestBody).collect {
                _registerResponse.emit(it)
            }
        }
    }

}