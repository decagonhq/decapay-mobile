package com.decagonhq.decapay.feature.verifypasswordresetcode.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeRequest
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.model.VerifyPasswordResetCodeResponse
import com.decagonhq.decapay.feature.verifypasswordresetcode.domain.verifypasswordresetcodeusecase.VerifyPasswordResetCodeUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VerifyPasswordResetCodeViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockVerifyPasswordResetCodeUsecase: VerifyPasswordResetCodeUsecase
    lateinit var mockVerifyPasswordResetCodeRequestBody: VerifyPasswordResetCodeRequest
    lateinit var mockResponse: VerifyPasswordResetCodeResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockVerifyPasswordResetCodeUsecase = mock(VerifyPasswordResetCodeUsecase::class.java)
        mockVerifyPasswordResetCodeRequestBody = mock(VerifyPasswordResetCodeRequest::class.java)
        mockResponse = mock(VerifyPasswordResetCodeResponse::class.java)
    }

    @Test
    fun verifyPasswordResetCodeViewModelTest_returnsASuccessfulData() = runTest {
        val flow = VerifyPasswordResetCodeFakeFlow(Resource.Success(mockResponse))
        `when`(mockVerifyPasswordResetCodeUsecase.invoke(mockVerifyPasswordResetCodeRequestBody)).thenReturn(flow)
        val viewModel = VerifyPasswordResetCodeViewModel(mockVerifyPasswordResetCodeUsecase, null)
        viewModel.getUserVerifyPasswordResetCode(mockVerifyPasswordResetCodeRequestBody)
        viewModel.verifyPasswordResetCode.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }
}
