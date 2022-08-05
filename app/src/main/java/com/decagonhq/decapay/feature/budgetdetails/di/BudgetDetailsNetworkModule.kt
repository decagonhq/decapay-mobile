package com.decagonhq.decapay.feature.budgetdetails.di

import com.decagonhq.decapay.feature.budgetdetails.data.api.BudgetDetailApi
import com.decagonhq.decapay.feature.budgetdetails.data.repository.BudgetDetailsRepositoryImpl
import com.decagonhq.decapay.feature.budgetdetails.domain.repository.BudgetDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


object BudgetDetailsNetworkModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object BidgetDetailsNetworkModule {

        @Provides
        @Singleton
        fun provideBudgetDetailsApi(retrofit: Retrofit): BudgetDetailApi {
            return retrofit.create(BudgetDetailApi::class.java)
        }

        @Provides
        @Singleton
        fun provideBudgetDetailsRepository(budgetDetailApi: BudgetDetailApi): BudgetDetailsRepository {
            return BudgetDetailsRepositoryImpl(budgetDetailApi)
        }

    }
}