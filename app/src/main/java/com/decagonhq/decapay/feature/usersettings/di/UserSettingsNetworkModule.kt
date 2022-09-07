package com.decagonhq.decapay.feature.usersettings.di

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetLocationListApi
import com.decagonhq.decapay.feature.usersettings.data.network.api.GetPreferedLanguageApi
import com.decagonhq.decapay.feature.usersettings.data.network.repository.GetLocationRepositoryImpl
import com.decagonhq.decapay.feature.usersettings.data.network.repository.GetPreferredLanguageRepositoryImpl
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetLocationRepository
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetPreferredLanguageRepository
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
    fun provideGetLocationListApi(retrofit: Retrofit): GetLocationListApi {
        return retrofit.create(GetLocationListApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetLocationListRepository(getLocationListApi: GetLocationListApi): GetLocationRepository {
        return GetLocationRepositoryImpl(getLocationListApi)
    }

    @Provides
    @Singleton
    fun provideGetPreferredLaguageApi(retrofit: Retrofit): GetPreferedLanguageApi {
        return retrofit.create(GetPreferedLanguageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetPreferredLanguageRepository(getPreferedLanguageApi: GetPreferedLanguageApi): GetPreferredLanguageRepository {
        return GetPreferredLanguageRepositoryImpl(getPreferedLanguageApi)
    }
}
