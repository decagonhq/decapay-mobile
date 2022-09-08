package com.decagonhq.decapay.feature.usersettings.di

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetLocalizationReferenceApi
import com.decagonhq.decapay.feature.usersettings.data.network.api.RegisterApi
import com.decagonhq.decapay.feature.usersettings.data.network.repository.GetLocalizationReferenceRepositoryImpl
import com.decagonhq.decapay.feature.usersettings.data.network.repository.RegisterRepositoryImpl
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetLocalizationReferenceRepository
import com.decagonhq.decapay.feature.usersettings.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserSettingsNetworkModule {

    @Provides
    @Singleton
    fun provideGetLocalizationReferenceApi(retrofit: Retrofit): GetLocalizationReferenceApi {
        return retrofit.create(GetLocalizationReferenceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetLocalizationReferenceRepository(getLocalizationReferenceApi: GetLocalizationReferenceApi): GetLocalizationReferenceRepository {
        return GetLocalizationReferenceRepositoryImpl(getLocalizationReferenceApi)
    }

    @Provides
    @Singleton
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(registerApi: RegisterApi): RegisterRepository {
        return RegisterRepositoryImpl(registerApi)
    }
}
