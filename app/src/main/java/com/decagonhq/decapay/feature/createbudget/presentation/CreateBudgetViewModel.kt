package com.decagonhq.decapay.feature.createbudget.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse
import com.decagonhq.decapay.feature.createbudget.domain.usecase.CreateBudgetUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBudgetViewModel @Inject constructor(
    private val createBudgetUsecase: CreateBudgetUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _createBudgetResponse = MutableSharedFlow<Resource<CreateBudgetResponse>>()
    val createBudgetResponse: SharedFlow<Resource<CreateBudgetResponse>> get() = _createBudgetResponse.asSharedFlow()

    fun userCreateBudget(authorization: String, createBudgetRequestBody: CreateBudgetRequestBody) {
        viewModelScope.launch {
            createBudgetUsecase(authorization, createBudgetRequestBody).collect {
                _createBudgetResponse.emit(it)
            }
        }
    }
}
