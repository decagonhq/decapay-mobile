package com.decagonhq.decapay.feature.logexpense.di

import com.decagonhq.decapay.feature.logexpense.data.network.api.LogExpenseApi
import com.decagonhq.decapay.feature.logexpense.data.network.repository.LogExpenseRepositoryImpl
import com.decagonhq.decapay.feature.logexpense.domain.repository.LogExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogExpenseNetworkModule {

    @Provides
    @Singleton
    fun provideLogExpenseApi(retrofit: Retrofit): LogExpenseApi {
        return retrofit.create(LogExpenseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLogExpenseRepository(logExpenseApi: LogExpenseApi): LogExpenseRepository {
        return LogExpenseRepositoryImpl(logExpenseApi)
    }

}