package com.decagonhq.decapay.feature.listbudget.di

import com.decagonhq.decapay.feature.listbudget.data.network.api.BudgetListApi
import com.decagonhq.decapay.feature.listbudget.data.repository.BudgetListRepositoryImpl
import com.decagonhq.decapay.feature.listbudget.domain.repository.BudgetListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BudgetListNetworkModule {

    @Provides
    @Singleton
    fun provideBudgetListApi(retrofit: Retrofit):BudgetListApi{
        return retrofit.create(BudgetListApi::class.java)
    }

    @Provides
    @Singleton
    fun providesBudgetListRepository(budgetListApi: BudgetListApi):BudgetListRepository{
        return  BudgetListRepositoryImpl(budgetListApi)
    }

}