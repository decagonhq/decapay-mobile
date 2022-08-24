package com.decagonhq.decapay.feature.expenseslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.domain.usecase.ExpenseListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val useCase: ExpenseListUseCase
) :ViewModel()
{

    private val _expenseListResponse = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val expenseListResponse: StateFlow<Resource<Any>> get() = _expenseListResponse

    var isFetching = false
    var page = 0


    fun getBudgetList() {
        viewModelScope.launch {

            useCase.invoke(page).collect {
                if (it is Resource.Success) {
                    page++
                }
                _expenseListResponse.value = it
            }
        }
    }

    fun getNextPage() {
        if (!isFetching) {
            isFetching = true
            viewModelScope.launch {
                useCase.getNextPage(page).collect {
                    if (it is Resource.Success) {
                        page++
                    }
                    isFetching = false
                    _expenseListResponse.value = it
                }
            }
        }
    }
}