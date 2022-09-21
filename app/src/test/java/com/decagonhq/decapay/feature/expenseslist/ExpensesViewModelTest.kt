package com.decagonhq.decapay.feature.expenseslist

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.expenseslist.data.network.model.Data
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseListResponse
import com.decagonhq.decapay.feature.expenseslist.domain.usecase.ExpenseListUseCase
import com.decagonhq.decapay.feature.expenseslist.presentation.ExpenseListViewModel
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExpensesViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockUseCase: ExpenseListUseCase
    private lateinit var mockResponse: ExpenseListResponse
    lateinit var mockData: Data

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUseCase = Mockito.mock(ExpenseListUseCase::class.java)
        mockResponse = Mockito.mock(ExpenseListResponse::class.java)
        mockData = Mockito.mock(Data::class.java)
    }

    @After
    fun testDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model goes into loading state`() {
        val viewModel = ExpenseListViewModel(mockUseCase,)
        assert(viewModel.expenseListResponse.value::class.java == Resource.Loading::class.java)
    }

    @Test
    fun `view model fetches first list`() = runTest {
        Mockito.`when`(mockData.last).thenReturn(true)
        Mockito.`when`(mockResponse.data).thenReturn(mockData)
        val flow = ResponseFakeFlow(Resource.Success(mockResponse))
        Mockito.`when`(mockUseCase.invoke(1, 1, 1)).thenReturn(flow)
        val viewModel = ExpenseListViewModel(mockUseCase)

        viewModel.getExpensesList(1, 1,)
        viewModel.expenseListResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun `view model fetches next list`() = runTest {
        Mockito.`when`(mockData.last).thenReturn(false)
        Mockito.`when`(mockResponse.data).thenReturn(mockData)
        val flow = ResponseFakeFlow(Resource.Success(mockResponse))
        val flow2 = ResponseFakeFlow(Resource.Success(mockResponse))
        Mockito.`when`(mockUseCase.invoke(1, 1, 1)).thenReturn(flow)
        Mockito.`when`(mockUseCase.getNextPage(1, 1, 2)).thenReturn(flow2)
        val viewModel = ExpenseListViewModel(mockUseCase)

        viewModel.getExpensesList(1, 1)

        viewModel.expenseListResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }

        viewModel.getNextPage(1, 1)

        viewModel.expenseListResponse.test {
            val thirdEmission = awaitItem()
            assert(thirdEmission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun `deleteWorks`() = runTest {
        val flow = DeleteFakeFlow(Resource.Success(mockResponse))
        Mockito.`when`(mockUseCase.deleteExpense(1)).thenReturn(flow)
        val viewModel = ExpenseListViewModel(mockUseCase)

        viewModel.deleteExpense(1,)
        viewModel.deleteResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }
}
