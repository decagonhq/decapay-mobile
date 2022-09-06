package com.decagonhq.decapay.feature.expenseslist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.data.network.model.DeleteResponse
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent
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
) : ViewModel() {

    private val _expenseListResponse =
        MutableStateFlow<Resource<List<ExpenseContent>>>(Resource.Loading())
    val expenseListResponse: StateFlow<Resource<List<ExpenseContent>>> get() = _expenseListResponse


    private val _deleteResponse = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val deleteResponse: StateFlow<Resource<Any>> get() = _deleteResponse

    var isFetching = false
    var page = 1
    var isLastPage = true


    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch {

            useCase.deleteExpense(expenseId).collect {

                _deleteResponse.value = it
            }

        }
    }


    ///

    private var list = mutableListOf<ExpenseContent>()


    fun getExpensesList(budgetId: Int, categoryId: Int) {
        list.clear()
        page = 1
        viewModelScope.launch {


            useCase.invoke(budgetId, categoryId, page).collect {
                when (it) {

                    is Resource.Success -> {
                        isLastPage = it.data.data.last
                        page++
                        it.datas?.data?.content?.let { it1 ->
                            list.addAll(it1)
                            _expenseListResponse.value = Resource.Success(list)
                        }

//

                    }
                    is Resource.Error -> {
                        _expenseListResponse.value = Resource.Error(it.message)
                    }
                    is Resource.Loading -> {
                        _expenseListResponse.value = Resource.Loading()
                    }
                }
            }
        }
    }

    fun getNextPage(budgetId: Int, categoryId: Int) {
        if (!isFetching && !isLastPage) {
            isFetching = true
            viewModelScope.launch {
                useCase.getNextPage(budgetId, categoryId, page).collect {
                    when (it) {

                        is Resource.Success -> {
                            isFetching = false
                            isLastPage = it.data.data.last
                            page++
                            it.datas?.data?.content?.let { dataList ->
                                val newList = mutableListOf<ExpenseContent>()
                                newList.addAll(list)
                                newList.addAll(dataList)
                                list = newList
                                _expenseListResponse.value = Resource.Success(newList)
                            }
                        }
                        is Resource.Error -> {
                            isFetching = false
                            _expenseListResponse.value = Resource.Error(it.message)
                        }
                        is Resource.Loading -> {
                            _expenseListResponse.value = Resource.Loading()
                        }
                    }

                }
            }
        }
    }
}