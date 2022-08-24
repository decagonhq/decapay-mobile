package com.decagonhq.decapay.feature.listbudget.presentation

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

    private val _budgetListResponse = MutableStateFlow<Resource<BudgetListResponse>>(Resource.Loading())
    val budgetListResponse: StateFlow<Resource<BudgetListResponse>> get() = _budgetListResponse

    var isFetching = false
    var page = 0

     fun getBudgetList() {
        viewModelScope.launch {

            budgetListUseCase.invoke(page).collect {
                if (it is Resource.Success) {
                    page++
                }
                _budgetListResponse.value = it
            }
        }
    }

    fun getNextPage() {
        if (!isFetching) {
            isFetching = true
            viewModelScope.launch {
                budgetListUseCase.getNextPage(page).collect {
                    if (it is Resource.Success) {
                        page++
                    }
                    isFetching = false
                    _budgetListResponse.value = it
                }
            }
        }
    }
}
