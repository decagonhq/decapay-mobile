package com.decagonhq.decapay.feature.logexpense.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBodyDemo
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo
import com.decagonhq.decapay.feature.logexpense.domain.usecase.AddExpenseUsecase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogExpenseViewModel @Inject constructor(
    private val addExpenseUsecase: AddExpenseUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _addExpenseResponse = MutableSharedFlow<Resource<LogExpenseResponseDemo>>()
    val addExpenseResponse: SharedFlow<Resource<LogExpenseResponseDemo>> get() = _addExpenseResponse.asSharedFlow()

    fun userAddExpense(logExpenseRequestBodyDemo: LogExpenseRequestBodyDemo) {
        viewModelScope.launch {
            addExpenseUsecase(logExpenseRequestBodyDemo).collect {
                _addExpenseResponse.emit(it)
            }
        }
    }

}