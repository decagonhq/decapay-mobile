package com.decagonhq.decapay.feature.forgotpassword.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import com.decagonhq.decapay.feature.forgotpassword.domain.usecase.ForgotPasswordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordRepositoryViewModel @Inject constructor(
    private val forgotPasswordUsecase: ForgotPasswordUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _forgotPasswordResponse = MutableSharedFlow<Resource<ForgotPasswordResponse>>()
    val forgotPasswordResponse: SharedFlow<Resource<ForgotPasswordResponse>> get() = _forgotPasswordResponse.asSharedFlow()

    fun activateForgotPassword(forgotPasswordRequest: ForgotPasswordRequest){
        viewModelScope.launch {
            forgotPasswordUsecase(forgotPasswordRequest).collect {
                _forgotPasswordResponse.emit(it)
            }
        }
    }
}
