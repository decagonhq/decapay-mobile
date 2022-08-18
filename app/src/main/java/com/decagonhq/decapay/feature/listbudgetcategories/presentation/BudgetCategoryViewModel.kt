package com.decagonhq.decapay.feature.listbudgetcategories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import com.decagonhq.decapay.feature.listbudgetcategories.domain.usecase.BudgetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BudgetCategoryViewModel @Inject constructor(
    private val budgetCategoryListUseCase: BudgetCategoryListUseCase
) :ViewModel() {

    private val _budgetCategoryListResponse = MutableStateFlow<Resource<BudgetCategoriesResponse>>(Resource.Loading())
    val budgetCategoryListResponse: StateFlow<Resource<BudgetCategoriesResponse>> get() = _budgetCategoryListResponse






    fun getBudgetCategoryList() {
        viewModelScope.launch {

            budgetCategoryListUseCase.invoke().collect {

                _budgetCategoryListResponse.value = it
            }
        }
    }
}