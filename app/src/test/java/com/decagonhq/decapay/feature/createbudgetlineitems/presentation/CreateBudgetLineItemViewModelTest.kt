package com.decagonhq.decapay.feature.createbudgetlineitems.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.createbudgetlineitemmodel.CreateBudgetLineItemResponse
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.usecase.CreateBudgetLineItemUsecase
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
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CreateBudgetLineItemViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockCreateBudgetLineItemResponse: CreateBudgetLineItemResponse
    lateinit var mockCreateBudgetLineItemRequestBody: CreateBudgetLineItemRequestBody
    lateinit var mockCreateBudgetLineItemUsecase: CreateBudgetLineItemUsecase
    var budgetId by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockCreateBudgetLineItemRequestBody = mock(CreateBudgetLineItemRequestBody::class.java)
        mockCreateBudgetLineItemResponse = mock(CreateBudgetLineItemResponse::class.java)
        mockCreateBudgetLineItemUsecase = mock(CreateBudgetLineItemUsecase::class.java)
        budgetId = 1
    }

    @Test
    fun createBudgetLineItemViewModelTest_returnASuccessfulResponse() = runTest {
        val flow = CreateBudgetLineItemFakeFlow(Resource.Success(mockCreateBudgetLineItemResponse))
        `when`(mockCreateBudgetLineItemUsecase(budgetId, mockCreateBudgetLineItemRequestBody)).thenReturn(flow)
        val viewModel = CreateBudgetLineItemViewModel(mockCreateBudgetLineItemUsecase, null)
        viewModel.userCreateBudgetLineItem(budgetId, mockCreateBudgetLineItemRequestBody)
        viewModel.createBudgetLineItemResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun createBudgetCategoryFragmentTest_returnAnErrorResponse() = runTest {
        val flow = CreateBudgetLineItemFakeFlow(Resource.Error("", mockCreateBudgetLineItemResponse))
        `when`(mockCreateBudgetLineItemUsecase(budgetId, mockCreateBudgetLineItemRequestBody)).thenReturn(flow)
        val viewModel = CreateBudgetLineItemViewModel(mockCreateBudgetLineItemUsecase, null)
        viewModel.userCreateBudgetLineItem(budgetId, mockCreateBudgetLineItemRequestBody)
        viewModel.createBudgetLineItemResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
