package com.decagonhq.decapay.feature.createnewpassword.di

import com.decagonhq.decapay.feature.createnewpassword.data.network.CreateNewPasswordRepositoryImpl
import com.decagonhq.decapay.feature.createnewpassword.data.network.api.CreateNewPasswordApi
import com.decagonhq.decapay.feature.createnewpassword.domain.repository.CreateNewPasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateNewPasswordNeworkModule {

    @Provides
    @Singleton
    fun provideCreateNewPasswordApi(retrofit: Retrofit): CreateNewPasswordApi {
        return retrofit.create(CreateNewPasswordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCreateNewPasswordRepository(createNewPasswordApi: CreateNewPasswordApi): CreateNewPasswordRepository {
        return CreateNewPasswordRepositoryImpl(createNewPasswordApi)
    }

}