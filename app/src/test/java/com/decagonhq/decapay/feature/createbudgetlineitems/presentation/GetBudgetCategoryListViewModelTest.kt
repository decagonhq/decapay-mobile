package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.GetBudgetCategoryListResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.usecase.GetBudgetCategeryUsecase
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetBudgetCategoryListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockGetBudgetLineItemResponse: GetBudgetCategoryListResponse
    lateinit var mockGetBudgetLineItemUsecase: GetBudgetCategeryUsecase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockGetBudgetLineItemResponse = mock(GetBudgetCategoryListResponse::class.java)
        mockGetBudgetLineItemUsecase = mock(GetBudgetCategeryUsecase::class.java)
    }

    @Test
    fun getBudgetLineItemViewModelTest_returnASuccessfulResponse() = runTest {
        val flow = GetBudgetLineItemFakeFlow(Resource.Success(mockGetBudgetLineItemResponse))
        `when`(mockGetBudgetLineItemUsecase()).thenReturn(flow)
        val viewModel = GetBudgetCategoryListViewModel(mockGetBudgetLineItemUsecase, null)
        viewModel.getBudgetCategoryList()
        viewModel.getBudgetCategoryListResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
