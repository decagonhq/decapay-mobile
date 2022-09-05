package com.decagonhq.decapay.feature.listbudget.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import com.decagonhq.decapay.feature.listbudget.domain.usecase.BudgetListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetListViewModel @Inject constructor(
    private val budgetListUseCase: BudgetListUseCase
) : ViewModel() {
    var budgetTypePosition: Int? = null
    var isLastPage = true

    private val _budgetListResponse = MutableStateFlow<Resource<BudgetListResponse>>(Resource.Loading())
    val budgetListResponse: StateFlow<Resource<BudgetListResponse>> get() = _budgetListResponse

    var isFetching = false
    var page = 1
    var state = ""

    fun getBudgetList(state: String) {
        Log.d("viewModel", "getBudgetList")
        this.state = state
        page = 1
        viewModelScope.launch {
            budgetListUseCase.invoke(page, state).collect {
                if (it is Resource.Success) {
                    isLastPage = it.data.data.last
                    page++
                }
                _budgetListResponse.value = it
            }
        }
    }

    fun getNextPage() {
        if (!isFetching && !isLastPage) {
            isFetching = true
            viewModelScope.launch {
                budgetListUseCase.getNextPage(page, state).collect {
                    if (it is Resource.Success) {
                        isLastPage = it.data.data.last
                        page++
                    }
                    isFetching = false
                    _budgetListResponse.value = it
                }
            }
        }
    }
}
