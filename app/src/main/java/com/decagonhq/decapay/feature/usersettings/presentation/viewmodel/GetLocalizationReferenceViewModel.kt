package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse
import com.decagonhq.decapay.feature.usersettings.domain.usecase.GetLocalizationReferenceUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetLocalizationReferenceViewModel @Inject constructor(
    private val getLocalizationReferenceUsecase: GetLocalizationReferenceUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel(){

    private val _getLocalizationReferenceResponse = MutableSharedFlow<Resource<GetLocalizationReferenceResponse>>()
    val getLocalizationReferenceResponse: SharedFlow<Resource<GetLocalizationReferenceResponse>> = _getLocalizationReferenceResponse.asSharedFlow()

    fun getUserLocalizationReference() {
        viewModelScope.launch {
            getLocalizationReferenceUsecase().collect {
                _getLocalizationReferenceResponse.emit(it)
            }
        }
    }

}