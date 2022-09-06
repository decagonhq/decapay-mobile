package com.decagonhq.decapay.feature.listbudget.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.utils.resource.Resource
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

    private val _budgetListResponse = MutableStateFlow<Resource<List<Content>>>(Resource.Loading())
    val budgetListResponse: StateFlow<Resource<List<Content>>> get() = _budgetListResponse

    private var list = mutableListOf<Content>()

    var isFetching = false
    var page = 1
    var state = ""

    fun getBudgetList(state: String) {
        list.clear()
        this.state = state
        page = 1
        viewModelScope.launch {
            budgetListUseCase.invoke(page, state).collect {
                when (it) {
                    is Resource.Success -> {
                        isLastPage = it.data.data.last
                        page++
                        it.datas?.data?.content?.let { it1 ->
                            list.addAll(it1)
                            _budgetListResponse.value = Resource.Success(list)
                        }
                    }
                    is Resource.Error -> {
                        _budgetListResponse.value = Resource.Error(it.message)
                    }
                    is Resource.Loading -> {
                        _budgetListResponse.value = Resource.Loading()
                    }
                }
            }
        }
    }

    fun getNextPage() {
        if (!isFetching && !isLastPage) {
            isFetching = true
            viewModelScope.launch {
                budgetListUseCase.getNextPage(page, state).collect {

                    when (it) {

                        is Resource.Success -> {
                            isFetching = false
                            isLastPage = it.data.data.last
                            page++
                            it.datas?.data?.content?.let { dataList ->
                                val newList = mutableListOf<Content>()
                                newList.addAll(list)
                                newList.addAll(dataList)
                                list = newList
                                _budgetListResponse.value = Resource.Success(newList)
                            }
                        }
                        is Resource.Error -> {
                            isFetching = false
                            _budgetListResponse.value = Resource.Error(it.message)
                        }
                        is Resource.Loading -> {
                            _budgetListResponse.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}
