package com.decagonhq.decapay.feature.editbudgetlineitem.di

import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.api.EditBudgetLineItemApi
import com.decagonhq.decapay.feature.editbudgetlineitem.data.network.repository.EditBudgetLineItemRepositoryImpl
import com.decagonhq.decapay.feature.editbudgetlineitem.domain.repository.EditBudgetLineItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditBudgetLineItemNetworkModule {

    @Provides
    @Singleton
    fun provideEditBudgetLineItemApi(retrofit: Retrofit): EditBudgetLineItemApi {
        return retrofit.create(EditBudgetLineItemApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEditBudgetLineItemRepository(editBudgetLineItemApi: EditBudgetLineItemApi): EditBudgetLineItemRepository {
        return EditBudgetLineItemRepositoryImpl(editBudgetLineItemApi)
    }
}
