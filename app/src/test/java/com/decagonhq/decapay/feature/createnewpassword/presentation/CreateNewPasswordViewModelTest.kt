package com.decagonhq.decapay.feature.createnewpassword.presentation

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordRequest
import com.decagonhq.decapay.feature.createnewpassword.data.network.model.CreateNewPasswordResponse
import com.decagonhq.decapay.feature.createnewpassword.domain.usecase.CreateNewPasswordUsecase
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
class CreateNewPasswordViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockCreateNewPasswordUsecase: CreateNewPasswordUsecase
    lateinit var mockCreateNewPasswordRequest: CreateNewPasswordRequest
    lateinit var mockCreateNewPasswordResponse: CreateNewPasswordResponse

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockCreateNewPasswordUsecase = mock(CreateNewPasswordUsecase::class.java)
        mockCreateNewPasswordRequest = mock(CreateNewPasswordRequest::class.java)
        mockCreateNewPasswordResponse = mock(CreateNewPasswordResponse::class.java)
    }

    @Test
    fun CreateNewPasswordViewModelTest_returnASuccessfulData() = runTest {
        val flow = CreateNewPasswordFakeFlow(Resource.Success(mockCreateNewPasswordResponse))
        `when`(mockCreateNewPasswordUsecase.invoke(mockCreateNewPasswordRequest)).thenReturn(flow)
        val viewModel = CreateNewPasswordViewModel(mockCreateNewPasswordUsecase, null)
        viewModel.getCreateNewPasswordResponse(mockCreateNewPasswordRequest)
        viewModel.createNewPasswordResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
