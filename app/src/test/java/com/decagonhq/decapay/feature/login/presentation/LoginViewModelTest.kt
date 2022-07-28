package com.decagonhq.decapay.feature.login.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.login.data.network.model.LoginRequestBody
import com.decagonhq.decapay.feature.login.data.network.model.LoginResponse
import com.decagonhq.decapay.feature.login.domain.usecase.LoginUsecase
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
class LoginViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockLoginUseCase: LoginUsecase
    lateinit var mockLoginRequestBody: LoginRequestBody

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockLoginUseCase = mock(LoginUsecase::class.java)
        mockLoginRequestBody = mock(LoginRequestBody::class.java)
    }

    @Test
    fun loginViewModel_returnsASuccessfulData() = runTest {
        val flow = LoginFakeFlow(Resource.Success(LoginResponse(null, null, null, "")))
        `when`(mockLoginUseCase.invoke(mockLoginRequestBody)).thenReturn(flow)
        val loginViewModel = LoginViewModel(mockLoginUseCase, null)
        loginViewModel.getUserLoggedIn(mockLoginRequestBody)
        loginViewModel.loginResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearnDown() {
        Dispatchers.resetMain()
    }
}
