package com.decagonhq.decapay.feature.logexpense.di

import com.decagonhq.decapay.feature.logexpense.data.network.api.LogExpenseApi
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

}