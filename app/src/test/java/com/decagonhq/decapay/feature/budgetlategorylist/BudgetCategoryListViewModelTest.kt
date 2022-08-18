package com.decagonhq.decapay.feature.budgetlategorylist

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.BudgetCategoriesResponse
import com.decagonhq.decapay.feature.listbudgetcategories.domain.usecase.BudgetCategoryListUseCase
import com.decagonhq.decapay.feature.listbudgetcategories.presentation.BudgetCategoryViewModel
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
class BudgetCategoryListViewModelTest {


    private val dispatcher = StandardTestDispatcher()
    lateinit var mockUseCase: BudgetCategoryListUseCase
    lateinit var mockResponse: BudgetCategoriesResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUseCase = Mockito.mock(BudgetCategoryListUseCase::class.java)
        mockResponse = Mockito.mock(BudgetCategoriesResponse::class.java)
    }

    @After
    fun testDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `view model goes into loading state`() {
        val viewModel = BudgetCategoryViewModel(mockUseCase,)
        assert(viewModel.budgetCategoryListResponse.value::class.java == Resource.Loading::class.java)
    }

    @Test
    fun `view model fetches list`() = runTest {

        val flow = ResponseFakeFlow(Resource.Success(mockResponse))
        Mockito.`when`(mockUseCase.invoke()).thenReturn(flow)
        val viewModel = BudgetCategoryViewModel(mockUseCase)

        viewModel.getBudgetCategoryList()
        viewModel.budgetCategoryListResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }

}