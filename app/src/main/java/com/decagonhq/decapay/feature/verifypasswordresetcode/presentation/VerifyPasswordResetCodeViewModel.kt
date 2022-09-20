package com.decagonhq.decapay.feature.verifypasswordresetcode.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import com.decagonhq.decapay.feature.verifypasswordresetcode.domain.verifypasswordresetcodeusecase.VerifyPasswordResetCodeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyPasswordResetCodeViewModel @Inject constructor(
    private val verifyPasswordResetCodeUsecase: VerifyPasswordResetCodeUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _verifyPasswordResetCode = MutableSharedFlow<Resource<VerifyPasswordResetCodeResponse>>()
    val verifyPasswordResetCode: SharedFlow<Resource<VerifyPasswordResetCodeResponse>> get() = _verifyPasswordResetCode.asSharedFlow()

    fun getUserVerifyPasswordResetCode(verifyPasswordResetCodeRequest: VerifyPasswordResetCodeRequest) {
        viewModelScope.launch {
            verifyPasswordResetCodeUsecase(verifyPasswordResetCodeRequest).collect {
                _verifyPasswordResetCode.emit(it)
            }
        }
    }
}
