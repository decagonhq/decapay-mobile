package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterRequestBody
import com.decagonhq.decapay.feature.usersettings.data.network.model.register.RegisterResponse
import com.decagonhq.decapay.feature.usersettings.domain.usecase.RegisterUsecase
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockRegisterRequestBody: RegisterRequestBody
    lateinit var mockRegisterUsecase: RegisterUsecase
    lateinit var mockRegisterResponse: RegisterResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockRegisterRequestBody = mock(RegisterRequestBody::class.java)
        mockRegisterUsecase = mock(RegisterUsecase::class.java)
        mockRegisterResponse = mock(RegisterResponse::class.java)
    }

    @Test
    fun registerViewModelTest_returnAnErrorResponse() = runTest {
        val flow = RegisterFakeFlow(Resource.Error("", mockRegisterResponse))
        `when`(mockRegisterUsecase(mockRegisterRequestBody)).thenReturn(flow)
        val viewModel = RegisterViewModel(mockRegisterUsecase, null)
        viewModel.registerUser(mockRegisterRequestBody)
        viewModel.registerResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @Test
    fun registerViewModelTest_returnASuccessResponse() = runTest {
        val flow = RegisterFakeFlow(Resource.Success(mockRegisterResponse))
        `when`(mockRegisterUsecase(mockRegisterRequestBody)).thenReturn(flow)
        val viewModel = RegisterViewModel(mockRegisterUsecase, null)
        viewModel.registerUser(mockRegisterRequestBody)
        viewModel.registerResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
