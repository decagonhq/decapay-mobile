package com.decagonhq.decapay.feature.listbudget

import app.cash.turbine.test
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudget.data.network.model.BudgetListResponse
import com.decagonhq.decapay.feature.listbudget.data.network.model.Data
import com.decagonhq.decapay.feature.listbudget.domain.usecase.BudgetListUseCase
import com.decagonhq.decapay.feature.listbudget.presentation.BudgetListViewModel
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
class BudgetListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockUseCase: BudgetListUseCase
    lateinit var mockResponse: BudgetListResponse
    lateinit var mockData: Data

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUseCase = Mockito.mock(BudgetListUseCase::class.java)
        mockResponse = Mockito.mock(BudgetListResponse::class.java)
        mockData = Mockito.mock(Data::class.java)
    }

    @After
    fun testDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model goes into loading state`() {
        val viewModel = BudgetListViewModel(mockUseCase,)
        assert(viewModel.budgetListResponse.value::class.java == Resource.Loading::class.java)
    }

    @Test
    fun `view model fetches first list`() = runTest {
        Mockito.`when`(mockData.last).thenReturn(true)
        Mockito.`when`(mockResponse.data).thenReturn(mockData)
        Mockito.`when`(mockResponse.data.content).thenReturn(mutableListOf<Content>())
        val flow = ResponseFakeFlow(Resource.Success(mockResponse))

        Mockito.`when`(mockUseCase.invoke(1,"")).thenReturn(flow)


        val viewModel = BudgetListViewModel(mockUseCase)

        viewModel.getBudgetList("")
        viewModel.budgetListResponse.test {
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
        Mockito.`when`(mockUseCase.invoke(1,"")).thenReturn(flow)
        Mockito.`when`(mockUseCase.getNextPage(2,"")).thenReturn(flow2)
        val viewModel = BudgetListViewModel(mockUseCase)

        viewModel.getBudgetList("")

        viewModel.budgetListResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }

        viewModel.getNextPage()

        viewModel.budgetListResponse.test {
            val thirdEmission = awaitItem()
            assert(thirdEmission::class.java == Resource.Success::class.java)
        }
    }
}
