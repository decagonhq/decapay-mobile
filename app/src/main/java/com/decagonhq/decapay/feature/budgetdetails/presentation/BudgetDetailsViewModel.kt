package com.decagonhq.decapay.feature.budgetdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.domain.usecase.BudgetsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetDetailsViewModel @Inject constructor(
    private val budgetsDetailsUseCase: BudgetsDetailsUseCase
):ViewModel(){
    private val _budgetDetailsResponse = MutableStateFlow<Resource<BudgetDetailsResponse>>(Resource.Loading())
    val budgetDetailsResponse: StateFlow<Resource<BudgetDetailsResponse>> get() = _budgetDetailsResponse




    fun getBudgetDetails(budgetId:Int, token:String){
        viewModelScope.launch {

            budgetsDetailsUseCase(budgetId,token).collect{
                _budgetDetailsResponse.value = it
            }

        }
    }







}