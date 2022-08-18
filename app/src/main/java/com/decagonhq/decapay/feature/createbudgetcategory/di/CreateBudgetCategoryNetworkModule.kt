package com.decagonhq.decapay.feature.createbudgetcategory.di

import com.decagonhq.decapay.feature.createbudgetcategory.data.network.api.CreateBudgetCategoryApi
import com.decagonhq.decapay.feature.createbudgetcategory.data.network.repository.CreateBudgetCategoryRepositoryImpl
import com.decagonhq.decapay.feature.createbudgetcategory.domain.repository.CreateBudgetCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateBudgetCategoryNetworkModule {

    @Provides
    @Singleton
    fun provideCreateBudgetCategoryApi(retrofit: Retrofit): CreateBudgetCategoryApi {
        return retrofit.create(CreateBudgetCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCreateBudgetCategoryRepository(createBudgetCategoryApi: CreateBudgetCategoryApi): CreateBudgetCategoryRepository {
        return CreateBudgetCategoryRepositoryImpl(createBudgetCategoryApi)
    }
}
