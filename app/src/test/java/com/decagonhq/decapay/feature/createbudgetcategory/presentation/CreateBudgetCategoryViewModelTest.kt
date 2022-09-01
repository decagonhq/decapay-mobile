package com.decagonhq.decapay.feature.createbudgetcategory.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.model.CreateBudgetCategoryResponse
import com.decagonhq.decapay.feature.createbudgetcategory.domain.usecase.CreateBudgetCategoryUsecase
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
class CreateBudgetCategoryViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockCreateBudgetCategoryUsecase: CreateBudgetCategoryUsecase
    lateinit var mockCreateBudgetCategoryResponse: CreateBudgetCategoryResponse
    lateinit var mockCreateBudgetCategoryRequestBody: CreateBudgetCategoryRequestBody

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockCreateBudgetCategoryUsecase = mock(CreateBudgetCategoryUsecase::class.java)
        mockCreateBudgetCategoryRequestBody = mock(CreateBudgetCategoryRequestBody::class.java)
        mockCreateBudgetCategoryResponse = mock(CreateBudgetCategoryResponse::class.java)
    }

    @Test
    fun createBudgetCategoryViewModelTest_returnAnErrorResponse() = runTest {
        val flow = CreateBudgetCategoryFakeFlow(Resource.Error("", mockCreateBudgetCategoryResponse))
        `when`(mockCreateBudgetCategoryUsecase(mockCreateBudgetCategoryRequestBody)).thenReturn(flow)
        val viewModel = CreateBudgetCategoryViewModel(mockCreateBudgetCategoryUsecase, null)
        viewModel.userCreateBudgetCategory(mockCreateBudgetCategoryRequestBody)
        viewModel.createBudgetCategoryCaptureResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @Test
    fun createBudgetCategoryViewModelTest_returnASuccessfulResponse() = runTest {
        val flow = CreateBudgetCategoryFakeFlow(Resource.Success(mockCreateBudgetCategoryResponse))
        `when`(mockCreateBudgetCategoryUsecase(mockCreateBudgetCategoryRequestBody)).thenReturn(flow)
        val viewModel = CreateBudgetCategoryViewModel(mockCreateBudgetCategoryUsecase, null)
        viewModel.userCreateBudgetCategory(mockCreateBudgetCategoryRequestBody)
        viewModel.createBudgetCategoryCaptureResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
