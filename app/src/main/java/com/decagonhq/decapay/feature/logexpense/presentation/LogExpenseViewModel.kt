package com.decagonhq.decapay.feature.logexpense.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBody
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponse
import com.decagonhq.decapay.feature.logexpense.domain.usecase.AddExpenseUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LogExpenseViewModel @Inject constructor(
    private val addExpenseUsecase: AddExpenseUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _addExpenseResponse = MutableSharedFlow<Resource<LogExpenseResponse>>()
    val addExpenseResponse: SharedFlow<Resource<LogExpenseResponse>> get() = _addExpenseResponse.asSharedFlow()

    fun userAddExpense(budgetId: Int, categoryId: Int, logExpenseRequestBody: LogExpenseRequestBody) {
        viewModelScope.launch {
            addExpenseUsecase(budgetId, categoryId, logExpenseRequestBody).collect {
                _addExpenseResponse.emit(it)
            }
        }
    }
}
