package com.decagonhq.decapay.feature.listbudget.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import com.decagonhq.decapay.feature.listbudget.domain.usecase.BudgetListUseCase
import com.decagonhq.decapay.feature.signup.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BudgetListViewModel @Inject constructor(
   private val budgetListUseCase: BudgetListUseCase
) :ViewModel(){

   private val _budgetListResponse = MutableStateFlow<Resource<BudgetListResponse>>(Resource.Loading())
   val budgetListResponse: StateFlow<Resource<BudgetListResponse>> get() = _budgetListResponse



   fun getBudgetList(token:String) {


      viewModelScope.launch {
       budgetListUseCase.invoke(token).collect{
          _budgetListResponse.value = it
       }
      }
   }


   fun getNextPage(token:String){
      viewModelScope.launch {
         budgetListUseCase.invoke(token).collect{
            _budgetListResponse.value = it
         }
      }
   }




}