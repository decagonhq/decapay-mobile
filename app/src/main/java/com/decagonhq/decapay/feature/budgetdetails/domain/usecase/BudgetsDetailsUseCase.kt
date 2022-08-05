package com.decagonhq.decapay.feature.budgetdetails.domain.usecase

import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.budgetdetails.domain.repository.BudgetDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BudgetsDetailsUseCase @Inject constructor(
    private val repository: BudgetDetailsRepository,
    private val exceptionHandler: ExceptionHandler
) {


    operator fun invoke(): Flow<Resource<Any>> =
        flow {

        }
}