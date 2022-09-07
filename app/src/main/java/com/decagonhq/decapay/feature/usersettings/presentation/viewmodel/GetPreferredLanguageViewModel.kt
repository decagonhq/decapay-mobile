package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetPreferredLanguageResponseDemo
import com.decagonhq.decapay.feature.usersettings.domain.usecase.GetPreferredLanguageUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPreferredLanguageViewModel @Inject constructor(
    private val getPreferredLanguageUsecase: GetPreferredLanguageUsecase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _getPreferredLanguageResponse = MutableSharedFlow<Resource<GetPreferredLanguageResponseDemo>>()
    val getPreferredLanguageResponse: SharedFlow<Resource<GetPreferredLanguageResponseDemo>> = _getPreferredLanguageResponse.asSharedFlow()

    fun getUserPreferredLanguage() {
        viewModelScope.launch {
            getPreferredLanguageUsecase().collect {
                _getPreferredLanguageResponse.emit(it)
            }
        }
    }

}