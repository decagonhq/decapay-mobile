package com.decagonhq.decapay.feature.editlogexpense.di

import com.decagonhq.decapay.feature.editlogexpense.data.network.api.EditLogExpenseApi
import com.decagonhq.decapay.feature.editlogexpense.data.network.repository.EditLogExpenseRepositoryImpl
import com.decagonhq.decapay.feature.editlogexpense.domain.repository.EditLogExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditLogExpenseNetworkModule {

    @Provides
    @Singleton
    fun provideEditLogExpenseApi(retrofit: Retrofit): EditLogExpenseApi {
        return retrofit.create(EditLogExpenseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEditLogExpenseRepository(editLogExpenseApi: EditLogExpenseApi): EditLogExpenseRepository {
        return EditLogExpenseRepositoryImpl(editLogExpenseApi)
    }

}
