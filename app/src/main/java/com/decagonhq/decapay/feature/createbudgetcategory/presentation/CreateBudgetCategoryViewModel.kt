package com.decagonhq.decapay.feature.createbudgetcategory.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse
import com.decagonhq.decapay.feature.createbudgetcategory.domain.usecase.CreateBudgetCategoryUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBudgetCategoryViewModel @Inject constructor(
    private val createBudgetCategoryUsecase: CreateBudgetCategoryUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _createBudgetCategoryCapturedResponse = MutableSharedFlow<Resource<CreateBudgetCategoryResponse>>()
    val createBudgetCategoryCaptureResponse: SharedFlow<Resource<CreateBudgetCategoryResponse>> get() = _createBudgetCategoryCapturedResponse.asSharedFlow()

    fun userCreateBudgetCategory(createBudgetCategoryRequestBody: CreateBudgetCategoryRequestBody) {
        viewModelScope.launch {
            createBudgetCategoryUsecase(createBudgetCategoryRequestBody).collect {
                _createBudgetCategoryCapturedResponse.emit(it)
            }
        }
    }

}
