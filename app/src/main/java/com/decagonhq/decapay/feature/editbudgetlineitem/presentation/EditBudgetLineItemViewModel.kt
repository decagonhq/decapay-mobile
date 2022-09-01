package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse
import com.decagonhq.decapay.feature.editbudgetlineitem.domain.usecase.UpdateBudgetLineItemUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBudgetLineItemViewModel @Inject constructor(
    private val updateBudgetLineItemUsecase: UpdateBudgetLineItemUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel(){

    private val _updateBudgetLineItemResponse = MutableSharedFlow<Resource<EditBudgetLineItemResponse>>()
    val updateBudgetLineItemResponse: SharedFlow<Resource<EditBudgetLineItemResponse>> get() = _updateBudgetLineItemResponse.asSharedFlow()

    fun updateBudgetLineItem(budgetId: Int, categoryId: Int, editBudgetLineItemRequestBody: EditBudgetLineItemRequestBody) {
        viewModelScope.launch {
            updateBudgetLineItemUsecase(budgetId, categoryId, editBudgetLineItemRequestBody).collect {
                _updateBudgetLineItemResponse.emit(it)
            }
        }
    }
}