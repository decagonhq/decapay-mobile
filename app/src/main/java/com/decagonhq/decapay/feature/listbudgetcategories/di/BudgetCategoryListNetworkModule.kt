package com.decagonhq.decapay.feature.listbudgetcategories.di

import com.decagonhq.decapay.feature.listbudgetcategories.data.network.api.BudgetCategoryApi
import com.decagonhq.decapay.feature.listbudgetcategories.data.repository.BudgetCategoryListRepositoryImpl
import com.decagonhq.decapay.feature.listbudgetcategories.domain.repository.BudgetCategoryListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BudgetCategoryListNetworkModule {

    @Provides
    @Singleton
    fun provideBudgetCategoryApi(retrofit: Retrofit):BudgetCategoryApi{
        return retrofit.create(BudgetCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun providesBudgetCategoryListRepository(budgetCategoryApi: BudgetCategoryApi):BudgetCategoryListRepository{
        return  BudgetCategoryListRepositoryImpl(budgetCategoryApi)
    }

}