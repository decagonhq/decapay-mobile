package com.decagonhq.decapay.feature.usersettings.presentation.viewmodel

import app.cash.turbine.test
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.feature.usersettings.data.network.model.GetLocalizationReferenceResponse
import com.decagonhq.decapay.feature.usersettings.domain.usecase.GetLocalizationReferenceUsecase
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
class GetLocalizationReferenceViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var mockGetLocalizationReferenceResponse: GetLocalizationReferenceResponse
    lateinit var mockGetLocalizationReferenceUsecase: GetLocalizationReferenceUsecase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockGetLocalizationReferenceResponse = mock(GetLocalizationReferenceResponse::class.java)
        mockGetLocalizationReferenceUsecase = mock(GetLocalizationReferenceUsecase::class.java)
    }

    @Test
    fun getLocalizationReferenceViewModelTest_returnAnErrorResponse() = runTest {
        val flow = GetLocalizationReferenceFakeFlow(Resource.Error("", mockGetLocalizationReferenceResponse))
        `when`(mockGetLocalizationReferenceUsecase()).thenReturn(flow)
        val viewModel = GetLocalizationReferenceViewModel(mockGetLocalizationReferenceUsecase, null)
        viewModel.getUserLocalizationReference()
        viewModel.getLocalizationReferenceResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Error::class.java)
        }
    }

    @Test
    fun getLocalizationReferenceViewModelTest_returnASuccessResponse() = runTest {
        val flow = GetLocalizationReferenceFakeFlow(Resource.Success(mockGetLocalizationReferenceResponse))
        `when`(mockGetLocalizationReferenceUsecase()).thenReturn(flow)
        val viewModel = GetLocalizationReferenceViewModel(mockGetLocalizationReferenceUsecase, null)
        viewModel.getUserLocalizationReference()
        viewModel.getLocalizationReferenceResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Success::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
