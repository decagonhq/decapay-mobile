package com.decagonhq.decapay.feature.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.login.data.network.model.LoginRequestBody
import com.decagonhq.decapay.feature.login.data.network.model.LoginResponse
import com.decagonhq.decapay.feature.login.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _loginResponse = MutableSharedFlow<Resource<LoginResponse>>()
    val loginResponse: SharedFlow<Resource<LoginResponse>> get() = _loginResponse.asSharedFlow()

    fun getUserLoggedIn(loginRequest: LoginRequestBody) {
        viewModelScope.launch {
            loginUsecase(loginRequest).collect {
                _loginResponse.emit(it)
            }
        }
    }
}
