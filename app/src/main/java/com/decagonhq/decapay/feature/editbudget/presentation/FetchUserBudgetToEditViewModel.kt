package com.decagonhq.decapay.feature.editbudget.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.usecase.FetchBudgetUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchUserBudgetToEditViewModel @Inject constructor(
    private val fetchBudgetUsecase: FetchBudgetUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _fetchBudgetToEditResponse = MutableSharedFlow<Resource<FetchEditBudgetResponse>>()
    val fetchEditBudgetResponse: SharedFlow<Resource<FetchEditBudgetResponse>> get() = _fetchBudgetToEditResponse.asSharedFlow()

    fun showUserBudgetDetailsToEdit(budgetId: Int) {
        viewModelScope.launch {
            fetchBudgetUsecase(budgetId).collect {
                _fetchBudgetToEditResponse.emit(it)
            }
        }
    }
}
