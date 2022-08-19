package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.usecase.GetBudgetCategeryUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetBudgetCategoryListViewModel @Inject constructor(
    private val getBudgetCategeryUsecase: GetBudgetCategeryUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _getBudgetCategoryListResponse = MutableSharedFlow<Resource<GetBudgetCategoryListResponse>>()
    val getBudgetCategoryListResponse: SharedFlow<Resource<GetBudgetCategoryListResponse>> get() = _getBudgetCategoryListResponse.asSharedFlow()

    fun getBudgetCategoryList() {
        viewModelScope.launch {
            getBudgetCategeryUsecase().collect {
                _getBudgetCategoryListResponse.emit(it)
            }
        }
    }
}
