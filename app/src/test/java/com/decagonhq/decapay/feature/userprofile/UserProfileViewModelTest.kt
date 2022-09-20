package com.decagonhq.decapay.feature.userprofile

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.BudgetDetailsResponse
import com.decagonhq.decapay.feature.budgetdetails.domain.usecase.BudgetsDetailsUseCase
import com.decagonhq.decapay.feature.budgetdetails.presentation.BudgetDetailsViewModel
import com.decagonhq.decapay.feature.userprofile.data.network.model.UserProfileResponse
import com.decagonhq.decapay.feature.userprofile.domain.usecase.UserProfileUsecase
import com.decagonhq.decapay.feature.userprofile.presentation.UserProfileViewModel
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
class UserProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var mockUseCase: UserProfileUsecase
    lateinit var mockResponse: UserProfileResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUseCase = Mockito.mock(UserProfileUsecase::class.java)
        mockResponse = Mockito.mock(UserProfileResponse::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model goes into loading state`() {
        val viewModel = UserProfileViewModel( mockUseCase,)
        assert(viewModel.userProfileResponse.value::class.java == Resource.Loading::class.java)
    }

    @Test
    fun `view model loads data`() = runTest{

        val flow = ResponseFakeFlow(Resource.Success(mockResponse))
        Mockito.`when`(mockUseCase.invoke()).thenReturn(flow)
        val  viewModel = UserProfileViewModel(mockUseCase)
        viewModel.getUserProfile()
        viewModel.userProfileResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }






}