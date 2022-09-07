package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.decagonhq.decapay.feature.usersettings.domain.usecase.GetPreferredCurrencyUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetPreferredCurrencyViewModel @Inject constructor(
    private val getPreferredCurrencyUsecase: GetPreferredCurrencyUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){



}