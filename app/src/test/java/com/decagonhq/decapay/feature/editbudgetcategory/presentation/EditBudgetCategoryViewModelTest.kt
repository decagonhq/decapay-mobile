package com.decagonhq.decapay.feature.editbudgetcategory.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryRequestBody
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.model.EditBudgetCategoryResponse
import com.decagonhq.decapay.feature.editbudgetcategory.domain.usecase.UpdateBudgetCategoryUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditBudgetCategoryViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockEditBudgetCategoryResponse: EditBudgetCategoryResponse
    lateinit var mockEditBudgetCategoryRequestBody: EditBudgetCategoryRequestBody
    lateinit var mockUpdateBudgetCategoryUsecase: UpdateBudgetCategoryUsecase
    var categoryId by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockEditBudgetCategoryRequestBody = Mockito.mock(EditBudgetCategoryRequestBody::class.java)
        mockEditBudgetCategoryResponse = Mockito.mock(EditBudgetCategoryResponse::class.java)
        mockUpdateBudgetCategoryUsecase = Mockito.mock(UpdateBudgetCategoryUsecase::class.java)
        categoryId = 1
    }

    @Test
    fun editBudgetCategoryiewModelTest_returnASuccessfulResponse() = runTest {
        val flow = EditBudgetCategoryFakeFlow(Resource.Success(mockEditBudgetCategoryResponse))
        `when`(mockUpdateBudgetCategoryUsecase(categoryId, mockEditBudgetCategoryRequestBody)).thenReturn(flow)
        val viewModel = EditBudgetCategoryViewModel(mockUpdateBudgetCategoryUsecase, null)
        viewModel.updateBudgetCategory(categoryId, mockEditBudgetCategoryRequestBody)
        viewModel.editBudgetCategoryResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun editBudgetCategoryiewModelTest_returnAnErrorResponse() = runTest {
        val flow = EditBudgetCategoryFakeFlow(Resource.Error("", mockEditBudgetCategoryResponse))
        `when`(mockUpdateBudgetCategoryUsecase(categoryId, mockEditBudgetCategoryRequestBody)).thenReturn(flow)
        val viewModel = EditBudgetCategoryViewModel(mockUpdateBudgetCategoryUsecase, null)
        viewModel.updateBudgetCategory(categoryId, mockEditBudgetCategoryRequestBody)
        viewModel.editBudgetCategoryResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }


}
