package com.decagonhq.decapay.feature.expenseslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.data.network.model.DeleteResponse
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse
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

    private val _expenseListResponse = MutableStateFlow<Resource<ExpenseListResponse>>(Resource.Loading())
    val expenseListResponse: StateFlow<Resource<ExpenseListResponse>> get() = _expenseListResponse


private val _deleteResponse = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val deleteResponse: StateFlow<Resource<Any>> get() = _deleteResponse

    var isFetching = false
    var page = 0


    fun getBudgetList(budgetId:Int,categoryId:Int) {
        viewModelScope.launch {

            useCase.invoke(budgetId,categoryId,page).collect {
                if (it is Resource.Success) {
                    page++
                }
                _expenseListResponse.value = it
            }
        }
    }

    fun getNextPage(budgetId:Int,categoryId:Int) {
        if (!isFetching) {
            isFetching = true
            viewModelScope.launch {
                useCase.getNextPage(budgetId,categoryId,page).collect {
                    if (it is Resource.Success) {
                        page++
                    }
                    isFetching = false
                    _expenseListResponse.value = it
                }
            }
        }
    }

    fun deleteExpense(expenseId: Int,){
        viewModelScope.launch {

            useCase.deleteExpense(expenseId).collect{

                _deleteResponse.value = it
            }

        }
    }
}