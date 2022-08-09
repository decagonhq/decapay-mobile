package com.decagonhq.decapay.feature.budgetdetails.di

import com.decagonhq.decapay.feature.budgetdetails.data.network.api.BudgetDetailsApi
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
        fun provideBudgetDetailsApi(retrofit: Retrofit): BudgetDetailsApi {
            return retrofit.create(BudgetDetailsApi::class.java)
        }

        @Provides
        @Singleton
        fun provideBudgetDetailsRepository(budgetDetailsApi: BudgetDetailsApi): BudgetDetailsRepository {
            return BudgetDetailsRepositoryImpl(budgetDetailsApi)
        }

    }
}