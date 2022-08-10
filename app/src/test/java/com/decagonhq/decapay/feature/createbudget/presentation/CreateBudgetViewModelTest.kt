package com.decagonhq.decapay.feature.createbudget.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetRequestBody
import com.decagonhq.decapay.feature.createbudget.data.network.model.CreateBudgetResponse
import com.decagonhq.decapay.feature.createbudget.domain.usecase.CreateBudgetUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CreateBudgetViewModelTest() {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockCreateBudgetResponse: CreateBudgetResponse
    lateinit var mockCreateBudgetResquest: CreateBudgetRequestBody
    lateinit var mockCreateBudgetUsecase: CreateBudgetUsecase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockCreateBudgetResponse = mock(CreateBudgetResponse::class.java)
        mockCreateBudgetResquest = mock(CreateBudgetRequestBody::class.java)
        mockCreateBudgetUsecase = mock(CreateBudgetUsecase::class.java)
    }

    @Test
    fun createBudgetViewModelTest_returnASuccessfulData() = runTest {
        val flow = CreateBudgetFakeFlow(Resource.Success(mockCreateBudgetResponse))
        `when`(mockCreateBudgetUsecase.invoke(mockCreateBudgetResquest)).thenReturn(flow)
        val createBudgetViewModel = CreateBudgetViewModel(mockCreateBudgetUsecase, null)
        createBudgetViewModel.userCreateBudget(mockCreateBudgetResquest)
        createBudgetViewModel.createBudgetResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
