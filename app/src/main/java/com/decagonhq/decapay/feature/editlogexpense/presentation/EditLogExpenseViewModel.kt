package com.decagonhq.decapay.feature.editlogexpense.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse
import com.decagonhq.decapay.feature.editlogexpense.domain.usecase.UpdateLogExpenseUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLogExpenseViewModel @Inject constructor(
    private val updateLogExpenseUsecase: UpdateLogExpenseUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _updateLogExpenseResponse = MutableSharedFlow<Resource<EditLogExpenseResponse>>()
    val updateLogExpenseResponse: SharedFlow<Resource<EditLogExpenseResponse>> get() = _updateLogExpenseResponse.asSharedFlow()

    fun userUpdateLogedExpense(expenseId: Int, editLogExpenseRequestBody: EditLogExpenseRequestBody) {
        viewModelScope.launch {
            updateLogExpenseUsecase(expenseId, editLogExpenseRequestBody).collect {
                _updateLogExpenseResponse.emit(it)
            }
        }
    }
}
