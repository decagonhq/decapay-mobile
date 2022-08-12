package com.decagonhq.decapay.feature.editbudget.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetRequestBody
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.usecase.UpdateEditBudgetUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UpdateEditBudgetViewModel @Inject constructor(
    private val updateEditBudgetUsecase: UpdateEditBudgetUsecase,
    private val savedStateHandle: SavedStateHandle?
): ViewModel() {

    private val _updateEditBudgetResponse = MutableSharedFlow<Resource<UpdateBudgetResponse>>()
    val updateBudgetResponse: SharedFlow<Resource<UpdateBudgetResponse>> get() = _updateEditBudgetResponse.asSharedFlow()

    fun userUpdateBudget(updateBudgetRequestBody: UpdateBudgetRequestBody, budgetInt: Int) {
        viewModelScope.launch {
            updateEditBudgetUsecase(updateBudgetRequestBody, budgetInt).collect {
                _updateEditBudgetResponse.emit(it)
            }
        }
    }

}