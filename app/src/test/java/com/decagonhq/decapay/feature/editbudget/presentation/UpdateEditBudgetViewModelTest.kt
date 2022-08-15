package com.decagonhq.decapay.feature.editbudget.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetRequestBody
import com.decagonhq.decapay.feature.editbudget.data.network.model.editbudgetmodel.UpdateBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.usecase.UpdateEditBudgetUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UpdateEditBudgetViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockUpdateEditBudgetResponse: UpdateBudgetResponse
    lateinit var mockUpdateEditBudgetRequestBody: UpdateBudgetRequestBody
    lateinit var mockUpdateEditBudgetUsecase: UpdateEditBudgetUsecase
    var budgetId by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUpdateEditBudgetResponse = mock(UpdateBudgetResponse::class.java)
        mockUpdateEditBudgetRequestBody = mock(UpdateBudgetRequestBody::class.java)
        mockUpdateEditBudgetUsecase = mock(UpdateEditBudgetUsecase::class.java)
        budgetId = 66
    }

    @Test
    fun updateEditBudgetViewModel_returnSuccessfulData() = runTest {
        val flow = UpdateEditBudgetFakeFlow(Resource.Success(mockUpdateEditBudgetResponse))
        `when`(mockUpdateEditBudgetUsecase.invoke(mockUpdateEditBudgetRequestBody, budgetId)).thenReturn(flow)
        val updateEditBudgetViewModel = UpdateEditBudgetViewModel(mockUpdateEditBudgetUsecase, null)
        updateEditBudgetViewModel.userUpdateBudget(mockUpdateEditBudgetRequestBody, budgetId)
        updateEditBudgetViewModel.updateBudgetResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(dispatcher)
    }
}
