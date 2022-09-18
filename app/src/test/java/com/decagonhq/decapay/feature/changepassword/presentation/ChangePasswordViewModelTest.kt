package com.decagonhq.decapay.feature.changepassword.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordRequestBody
import com.decagonhq.decapay.feature.changepassword.data.network.model.ChangePasswordResponse
import com.decagonhq.decapay.feature.changepassword.domain.usecase.ChangePasswordUsecase
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
class ChangePasswordViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockChangePasswordResponse: ChangePasswordResponse
    lateinit var mockChangePasswordUsecase: ChangePasswordUsecase
    lateinit var mockChangePasswordRequestBody: ChangePasswordRequestBody

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockChangePasswordRequestBody = mock(ChangePasswordRequestBody::class.java)
        mockChangePasswordResponse = mock(ChangePasswordResponse::class.java)
        mockChangePasswordUsecase = mock(ChangePasswordUsecase::class.java)
    }

    @Test
    fun changePasswordViewModel_returnASuccessfulResponse() = runTest {
        val flow = ChangePasswordFakeFlow(Resource.Success(mockChangePasswordResponse))
        `when`(mockChangePasswordUsecase(mockChangePasswordRequestBody)).thenReturn(flow)
        val viewModel = ChangePasswordViewModel(mockChangePasswordUsecase, null)
        viewModel.userChangePassword(mockChangePasswordRequestBody)
        viewModel.changePasswordResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun changePasswordViewModel_returnAnErrorResponse() = runTest {
        val flow = ChangePasswordFakeFlow(Resource.Error("", mockChangePasswordResponse))
        `when`(mockChangePasswordUsecase(mockChangePasswordRequestBody)).thenReturn(flow)
        val viewModel = ChangePasswordViewModel(mockChangePasswordUsecase, null)
        viewModel.userChangePassword(mockChangePasswordRequestBody)
        viewModel.changePasswordResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
