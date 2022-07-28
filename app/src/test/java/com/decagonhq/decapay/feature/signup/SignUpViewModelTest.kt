package com.decagonhq.decapay.feature.signup

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpRequestBody
import com.decagonhq.decapay.feature.signup.data.network.model.SignUpResponse
import com.decagonhq.decapay.feature.signup.domain.usecase.SignUpUseCase
import com.decagonhq.decapay.feature.signup.presentation.SignUpViewModel
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.test.* // ktlint-disable no-wildcard-imports
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SignUpViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockUseCase: SignUpUseCase
    lateinit var mockRequestBody: SignUpRequestBody
    lateinit var mockResponse: SignUpResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockUseCase = mock(SignUpUseCase::class.java)
        mockRequestBody = mock(SignUpRequestBody::class.java)
        mockResponse = mock(SignUpResponse::class.java)
    }

    @Test
    fun `view model goes into loading state`() {
        val viewModel = SignUpViewModel(useCase = mockUseCase,)
        assert(viewModel.registerResponse.value::class.java == Resource.Loading::class.java)
    }

    @Test
    fun `view model returns successful data`() = runTest {

        val flow = FakeFlow(Resource.Success(mockResponse))
        `when`(mockUseCase.invoke(mockRequestBody)).thenReturn(flow)
        val viewModel = SignUpViewModel(useCase = mockUseCase)
        viewModel.signUp(mockRequestBody)
        viewModel.registerResponse.test {
            /** first emission will be emitted as loading**/
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }

    @Test
    fun `view model returns failed data`() = runTest {

        val flow = FakeFlow(Resource.Error(message = "Error Occured",))
        `when`(mockUseCase.invoke(mockRequestBody)).thenReturn(flow)
        val viewModel = SignUpViewModel(useCase = mockUseCase)
        viewModel.signUp(mockRequestBody)
        viewModel.registerResponse.test {
            /** first emission will be emitted as loading**/
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Error::class.java)
        }
    }

    @After
    fun testDown() {
        Dispatchers.resetMain()
    }
}
