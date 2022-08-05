package com.decagonhq.decapay.feature.budgetdetails.presentation

import androidx.lifecycle.ViewModel
import com.decagonhq.decapay.feature.budgetdetails.domain.usecase.BudgetsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BudgetDetailsViewModel @Inject constructor(
    private val budgetsDetailsUseCase: BudgetsDetailsUseCase
):ViewModel(){







}