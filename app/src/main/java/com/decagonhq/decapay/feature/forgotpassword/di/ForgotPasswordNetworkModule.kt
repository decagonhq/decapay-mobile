package com.decagonhq.decapay.feature.forgotpassword.di

import com.decagonhq.decapay.feature.forgotpassword.data.network.api.ForgotPasswordApi
import com.decagonhq.decapay.feature.forgotpassword.data.network.repository.ForgotPasswordRepositoryImpl
import com.decagonhq.decapay.feature.forgotpassword.domain.repository.ForgotPasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForgotPasswordNetworkModule {

    @Provides
    @Singleton
    fun provideForgotPasswordApi(retrofit: Retrofit): ForgotPasswordApi {
        return retrofit.create(ForgotPasswordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideForgotPasswordRepository(forgotPasswordApi: ForgotPasswordApi): ForgotPasswordRepository {
        return ForgotPasswordRepositoryImpl(forgotPasswordApi)
    }
}
