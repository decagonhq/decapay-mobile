package com.decagonhq.decapay.feature.changepassword.di

import com.decagonhq.decapay.feature.changepassword.data.network.api.ChangePasswordApi
import com.decagonhq.decapay.feature.changepassword.data.network.repository.ChangePasswordRepositoryImpl
import com.decagonhq.decapay.feature.changepassword.domain.repository.ChangePasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChangePasswordNetworkModule {

    @Provides
    @Singleton
    fun provideChangePasswordApi(retrofit: Retrofit): ChangePasswordApi {
        return retrofit.create(ChangePasswordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChangePasswordRepository(changePasswordApi: ChangePasswordApi): ChangePasswordRepository {
        return ChangePasswordRepositoryImpl(changePasswordApi)
    }
}
