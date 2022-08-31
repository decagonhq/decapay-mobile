package com.decagonhq.decapay.feature.editlogexpense.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseRequestBody
import com.decagonhq.decapay.feature.editlogexpense.data.network.model.EditLogExpenseResponse
import com.decagonhq.decapay.feature.editlogexpense.domain.usecase.UpdateLogExpenseUsecase
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
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditLogExpenseViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockEditLogExpenseResponse: EditLogExpenseResponse
    lateinit var mockEditLogExpenseRequestBody: EditLogExpenseRequestBody
    lateinit var mockUpdateLogExpenseUsecase: UpdateLogExpenseUsecase
    private var expenseId by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockEditLogExpenseResponse = mock(EditLogExpenseResponse::class.java)
        mockEditLogExpenseRequestBody = mock(EditLogExpenseRequestBody::class.java)
        mockUpdateLogExpenseUsecase = mock(UpdateLogExpenseUsecase::class.java)
        expenseId = 1
    }

    @Test
    fun editLogExpenseViewModelTest_returnASuccessfulResponse() = runTest {
        val flow = EditLogExpenseFakeFlow(Resource.Success(mockEditLogExpenseResponse))
        Mockito.`when`(mockUpdateLogExpenseUsecase(expenseId, mockEditLogExpenseRequestBody))
            .thenReturn(flow)
        val viewModel = EditLogExpenseViewModel(mockUpdateLogExpenseUsecase, null)
        viewModel.userUpdateLogedExpense(expenseId, mockEditLogExpenseRequestBody)
        viewModel.updateLogExpenseResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun editLogExpenseViewModelTest_returnAnErrorResponse() = runTest {
        val flow = EditLogExpenseFakeFlow(Resource.Error("", mockEditLogExpenseResponse))
        Mockito.`when`(mockUpdateLogExpenseUsecase(expenseId, mockEditLogExpenseRequestBody))
            .thenReturn(flow)
        val viewModel = EditLogExpenseViewModel(mockUpdateLogExpenseUsecase, null)
        viewModel.userUpdateLogedExpense(expenseId, mockEditLogExpenseRequestBody)
        viewModel.updateLogExpenseResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
