package com.decagonhq.decapay.feature.logexpense.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseRequestBodyDemo
import com.decagonhq.decapay.feature.logexpense.data.network.model.LogExpenseResponseDemo
import com.decagonhq.decapay.feature.logexpense.domain.usecase.AddExpenseUsecase
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
class LogExpenseViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockLogExpenseResponse: LogExpenseResponseDemo
    lateinit var mockLogExpenseRequestBody: LogExpenseRequestBodyDemo
    lateinit var mockAddExpenseUsecase: AddExpenseUsecase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockLogExpenseResponse = mock(LogExpenseResponseDemo::class.java)
        mockLogExpenseRequestBody = mock(LogExpenseRequestBodyDemo::class.java)
        mockAddExpenseUsecase = mock(AddExpenseUsecase::class.java)
    }

    @Test
    fun LogExpenseViewModelTest_returnASuccessfulResponse() = runTest {
        val flow = LogExpenseFakeFlow(Resource.Success(mockLogExpenseResponse))
        `when`(mockAddExpenseUsecase(mockLogExpenseRequestBody)).thenReturn(flow)
        val viewModel = LogExpenseViewModel(mockAddExpenseUsecase, null)
        viewModel.userAddExpense(mockLogExpenseRequestBody)
        viewModel.addExpenseResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
