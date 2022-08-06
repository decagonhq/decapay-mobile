package com.decagonhq.decapay.feature.createbudget.di

import com.decagonhq.decapay.feature.createbudget.data.network.api.CreateBudgetApi
import com.decagonhq.decapay.feature.createbudget.data.network.repository.CreateBudgetProjectRepositoryImpl
import com.decagonhq.decapay.feature.createbudget.domain.repository.CreateBudgetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateBudgetNetworkModule {

    @Provides
    @Singleton
    fun provideCreateBudgetApi(retrofit: Retrofit): CreateBudgetApi {
        return retrofit.create(CreateBudgetApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCreateBudgetRepository(createBudgetApi: CreateBudgetApi): CreateBudgetRepository {
        return CreateBudgetProjectRepositoryImpl(createBudgetApi)
    }

}