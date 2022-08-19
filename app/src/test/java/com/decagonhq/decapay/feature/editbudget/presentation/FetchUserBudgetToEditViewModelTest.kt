package com.decagonhq.decapay.feature.editbudget.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.editbudget.data.network.model.FetchEditBudgetResponse
import com.decagonhq.decapay.feature.editbudget.domain.usecase.FetchBudgetUsecase
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
class FetchUserBudgetToEditViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockEditBudgetResponse: FetchEditBudgetResponse
    lateinit var mockFetchEditBudgetUsecase: FetchBudgetUsecase
    var budgetId by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockFetchEditBudgetUsecase = mock(FetchBudgetUsecase::class.java)
        mockEditBudgetResponse = mock(FetchEditBudgetResponse::class.java)
        budgetId = 66
    }

    @Test
    fun fetchBudgetToEditViewModel_returnSuccessfulData() = runTest {
        val flow = FetchEditBudgetFakeFlow(Resource.Success(mockEditBudgetResponse))
        `when`(mockFetchEditBudgetUsecase.invoke(budgetId)).thenReturn(flow)
        val fetchEditBudgetToEditViewModel = FetchUserBudgetToEditViewModel(mockFetchEditBudgetUsecase, null)
        fetchEditBudgetToEditViewModel.showUserBudgetDetailsToEdit(budgetId)
        fetchEditBudgetToEditViewModel.fetchEditBudgetResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
