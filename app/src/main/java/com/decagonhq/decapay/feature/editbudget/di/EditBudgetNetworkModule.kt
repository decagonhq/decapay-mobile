package com.decagonhq.decapay.feature.editbudget.di

import com.decagonhq.decapay.feature.editbudget.data.network.api.EditBudgetApi
import com.decagonhq.decapay.feature.editbudget.data.network.repository.FetchBudgetToEditRepositoryImpl
import com.decagonhq.decapay.feature.editbudget.data.network.repository.UpdateEditBudgetRepositoryImpl
import com.decagonhq.decapay.feature.editbudget.domain.repository.FetchUserBudgetToEditRepository
import com.decagonhq.decapay.feature.editbudget.domain.repository.UpdateEditBudgetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditBudgetNetworkModule {

    @Provides
    @Singleton
    fun provideFetchBudgetToEditApi(retrofit: Retrofit): EditBudgetApi {
        return retrofit.create(EditBudgetApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFetchBudgetToEditRepository(editBudgetApi: EditBudgetApi): FetchUserBudgetToEditRepository {
        return FetchBudgetToEditRepositoryImpl(editBudgetApi)
    }

    @Provides
    @Singleton
    fun provideUpdateEditBudgetRepository(editBudgetApi: EditBudgetApi): UpdateEditBudgetRepository {
        return UpdateEditBudgetRepositoryImpl(editBudgetApi)
    }

}
