package com.decagonhq.decapay.feature.budgetdetails

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.domain.usecase.BudgetsDetailsUseCase
import com.decagonhq.decapay.feature.budgetdetails.presentation.BudgetDetailsViewModel
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
class BudgetDetailsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockUseCase: BudgetsDetailsUseCase
    lateinit var mockResponse: BudgetDetailsResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUseCase = Mockito.mock(BudgetsDetailsUseCase::class.java)
        mockResponse = Mockito.mock(BudgetDetailsResponse::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model goes into loading state`() {
        val viewModel = BudgetDetailsViewModel( mockUseCase,)
        assert(viewModel.budgetDetailsResponse.value::class.java == Resource.Loading::class.java)
    }

    @Test
    fun `view model loads data`() = runTest{

        val flow = ResponseFakeFlow(Resource.Success(mockResponse))
        Mockito.`when`(mockUseCase.invoke(1)).thenReturn(flow)
        val  viewModel = BudgetDetailsViewModel(mockUseCase)
        viewModel.getBudgetDetails(1)
        viewModel.budgetDetailsResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }






}