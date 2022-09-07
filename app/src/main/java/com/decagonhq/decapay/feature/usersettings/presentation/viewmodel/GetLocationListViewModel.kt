package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocationResponseDemo
import com.decagonhq.decapay.feature.usersettings.domain.usecase.GetLocationListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetLocationListViewModel @Inject constructor(
    private val getLocationListUsecase: GetLocationListUsecase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _getLocationListResponse = MutableSharedFlow<Resource<GetLocationResponseDemo>>()
    val getLocationListResponse: SharedFlow<Resource<GetLocationResponseDemo>> = _getLocationListResponse.asSharedFlow()

    fun getUserLocation() {
        viewModelScope.launch {
            getLocationListUsecase().collect {
                _getLocationListResponse.emit(it)
            }
        }
    }

}