package com.decagonhq.decapay.feature.editbudgetcategory.di

import com.decagonhq.decapay.feature.editbudgetcategory.data.network.api.EditBudgetCategoryApi
import com.decagonhq.decapay.feature.editbudgetcategory.data.network.repository.EditBudgetCategoryRepositoryImpl
import com.decagonhq.decapay.feature.editbudgetcategory.domain.repository.EditBudgetCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditBudgetCategoryNetworkModule {

    @Provides
    @Singleton
    fun provideEditBudgetCategoryApi(retrofit: Retrofit): EditBudgetCategoryApi {
        return retrofit.create(EditBudgetCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEditBudgetCategoryRepository(editBudgetCategoryApi: EditBudgetCategoryApi) : EditBudgetCategoryRepository {
        return EditBudgetCategoryRepositoryImpl(editBudgetCategoryApi)
    }
}
