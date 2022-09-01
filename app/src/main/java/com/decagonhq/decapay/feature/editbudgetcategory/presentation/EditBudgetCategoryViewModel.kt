package com.decagonhq.decapay.feature.editbudgetcategory.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse
import com.decagonhq.decapay.feature.editbudgetcategory.domain.usecase.UpdateBudgetCategoryUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBudgetCategoryViewModel @Inject constructor(
    private val editBudgetCategoryUsecase: UpdateBudgetCategoryUsecase,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val _editBudgetCategoryResponse = MutableSharedFlow<Resource<EditBudgetCategoryResponse>>()
    val editBudgetCategoryResponse: SharedFlow<Resource<EditBudgetCategoryResponse>> get() = _editBudgetCategoryResponse.asSharedFlow()

    fun updateBudgetCategory(categoryId: Int, editBudgetCategoryRequestBody: EditBudgetCategoryRequestBody) {
        viewModelScope.launch {
            editBudgetCategoryUsecase(categoryId, editBudgetCategoryRequestBody).collect {
                _editBudgetCategoryResponse.emit(it)
            }
        }
    }
}
