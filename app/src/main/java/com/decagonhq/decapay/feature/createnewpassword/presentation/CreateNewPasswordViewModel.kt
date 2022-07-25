package com.decagonhq.decapay.feature.createnewpassword.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import com.decagonhq.decapay.feature.createnewpassword.domain.usecase.CreateNewPasswordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewPasswordViewModel @Inject constructor(
    private val createNewPasswordUsecase: CreateNewPasswordUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _createNewPasswordResponse = MutableSharedFlow<Resource<CreateNewPasswordResponse>>()
    val createNewPasswordResponse: SharedFlow<Resource<CreateNewPasswordResponse>> get() = _createNewPasswordResponse.asSharedFlow()

    fun getCreateNewPasswordResponse(createNewPasswordRequest: CreateNewPasswordRequest) {
        viewModelScope.launch { createNewPasswordUsecase(createNewPasswordRequest).collect {
            _createNewPasswordResponse.emit(it)
        }
        }
    }
}
