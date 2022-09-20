package com.decagonhq.decapay.feature.changepassword.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordRequestBody
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse
import com.decagonhq.decapay.feature.changepassword.domain.usecase.ChangePasswordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUsecase: ChangePasswordUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _changePasswordResponse = MutableSharedFlow<Resource<ChangePasswordResponse>>()
    val changePasswordResponse: SharedFlow<Resource<ChangePasswordResponse>> get() = _changePasswordResponse.asSharedFlow()

    fun userChangePassword(changePasswordRequestBody: ChangePasswordRequestBody) {
        viewModelScope.launch { changePasswordUsecase(changePasswordRequestBody).collect {
            _changePasswordResponse.emit(it)
        }
        }
    }
}
