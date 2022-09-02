package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.usecase.CreateBudgetLineItemUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBudgetLineItemViewModel @Inject constructor(
    private val createBudgetLineItemUsecase: CreateBudgetLineItemUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _createBudgetLineItemResponse = MutableSharedFlow<Resource<CreateBudgetLineItemResponse>>()
    val createBudgetLineItemResponse: SharedFlow<Resource<CreateBudgetLineItemResponse>> get() = _createBudgetLineItemResponse.asSharedFlow()

    fun userCreateBudgetLineItem(budgetId: Int, createBudgetLineItemRequestBody: CreateBudgetLineItemRequestBody) {
        viewModelScope.launch {
            createBudgetLineItemUsecase(budgetId, createBudgetLineItemRequestBody).collect {
                _createBudgetLineItemResponse.emit(it)
            }
        }
    }
}
