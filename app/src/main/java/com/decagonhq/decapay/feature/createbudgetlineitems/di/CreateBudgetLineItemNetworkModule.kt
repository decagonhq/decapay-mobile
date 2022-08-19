package com.decagonhq.decapay.feature.createbudgetlineitems.di

import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.api.CreateBudgetLineItemApi
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.api.GetBudgetCategoryListApi
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.repository.GetBudgetCategoryListRepositoryImpl
import com.decagonhq.decapay.feature.createbudgetlineitems.domain.repository.GetBudgetCategoryListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateBudgetLineItemNetworkModule {

    @Provides
    @Singleton
    fun provideGetGetBudgetCategoryListApi(retrofit: Retrofit): GetBudgetCategoryListApi {
        return retrofit.create(GetBudgetCategoryListApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetBudgetCategoryListRepository(getBudgetCategoryListApi: GetBudgetCategoryListApi): GetBudgetCategoryListRepository {
        return GetBudgetCategoryListRepositoryImpl(getBudgetCategoryListApi)
    }

    @Provides
    @Singleton
    fun provideCreateBudgetLineItemApi(retrofit: Retrofit): CreateBudgetLineItemApi {
        return retrofit.create(CreateBudgetLineItemApi::class.java)
    }
}
