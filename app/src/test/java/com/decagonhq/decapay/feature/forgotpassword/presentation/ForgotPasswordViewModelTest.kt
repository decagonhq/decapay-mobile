package com.decagonhq.decapay.feature.forgotpassword.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordRequest
import com.decagonhq.decapay.feature.forgotpassword.data.network.model.ForgotPasswordResponse
import com.decagonhq.decapay.feature.forgotpassword.domain.usecase.ForgotPasswordUsecase
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
class ForgotPasswordViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockForgotPasswordUsecase: ForgotPasswordUsecase
    lateinit var mockForgotPasswordRequest: ForgotPasswordRequest
    lateinit var mockResponse: ForgotPasswordResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockForgotPasswordUsecase = mock(ForgotPasswordUsecase::class.java)
        mockForgotPasswordRequest = mock(ForgotPasswordRequest::class.java)
        mockResponse = mock(ForgotPasswordResponse::class.java)
    }

    @Test
    fun forgotPasswordViewModelTest_returnsASuccessfulData() = runTest {
        val flow = ForgotPasswordFakeFlow(Resource.Success(mockResponse))
        `when`(mockForgotPasswordUsecase.invoke(mockForgotPasswordRequest)).thenReturn(flow)
        val viewModel = ForgotPasswordViewModel(mockForgotPasswordUsecase, null)
        viewModel.activateForgotPassword(mockForgotPasswordRequest)
        viewModel.forgotPasswordResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
