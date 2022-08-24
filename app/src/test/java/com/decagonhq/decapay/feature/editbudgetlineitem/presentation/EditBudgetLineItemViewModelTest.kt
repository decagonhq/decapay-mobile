package com.decagonhq.decapay.feature.editbudgetlineitem.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemRequestBody
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.model.EditBudgetLineItemResponse
import com.decagonhq.decapay.feature.editbudgetlineitem.domain.usecase.UpdateBudgetLineItemUsecase
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
class EditBudgetLineItemViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockEditBudgetLineItemResponse: EditBudgetLineItemResponse
    lateinit var mockEditBudgetLineItemRequestBody: EditBudgetLineItemRequestBody
    lateinit var mockEditBudgetLineItemUsecase: UpdateBudgetLineItemUsecase
    private var budgetId by Delegates.notNull<Int>()
    private var categoryId by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockEditBudgetLineItemRequestBody = mock(EditBudgetLineItemRequestBody::class.java)
        mockEditBudgetLineItemResponse = mock(EditBudgetLineItemResponse::class.java)
        mockEditBudgetLineItemUsecase = mock(UpdateBudgetLineItemUsecase::class.java)
        budgetId = 86
        categoryId = 17
    }

    @Test
    fun editBudgetLineItemViewModelTest_returnAnErrorResponse() = runTest {
        val flow = EditBudgetLineItemFakeFlow(Resource.Error("", mockEditBudgetLineItemResponse))
        `when`(mockEditBudgetLineItemUsecase(budgetId, categoryId, mockEditBudgetLineItemRequestBody)).thenReturn(flow)
        val viewModel = EditBudgetLineItemViewModel(mockEditBudgetLineItemUsecase, null)
        viewModel.updateBudgetLineItem(budgetId, categoryId, mockEditBudgetLineItemRequestBody)
        viewModel.updateBudgetLineItemResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @Test
    fun editBudgetLineItemViewModelTest_returnASuccessfulResponse() = runTest {
        val flow = EditBudgetLineItemFakeFlow(Resource.Success(mockEditBudgetLineItemResponse))
        `when`(mockEditBudgetLineItemUsecase(budgetId, categoryId, mockEditBudgetLineItemRequestBody)).thenReturn(flow)
        val viewModel = EditBudgetLineItemViewModel(mockEditBudgetLineItemUsecase, null)
        viewModel.updateBudgetLineItem(budgetId, categoryId, mockEditBudgetLineItemRequestBody)
        viewModel.updateBudgetLineItemResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
