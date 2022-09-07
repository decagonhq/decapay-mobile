package com.decagonhq.decapay.feature.usersettings.di

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetLocalizationReferenceApi
import com.decagonhq.decapay.feature.usersettings.data.network.repository.GetLocalizationReferenceRepositoryImpl
import com.decagonhq.decapay.feature.usersettings.domain.repository.GetLocalizationReferenceRepository
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

    @Provides
    @Singleton
    fun provideGetPreferredCurrencyApi(retrofit: Retrofit): GetPreferredCurrencyApi {
        return retrofit.create(GetPreferredCurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetPreferredCurrencyRepository(getPreferredCurrencyApi: GetPreferredCurrencyApi): GetPreferredCurrencyRepository {
        return GetPreferredCurrencyRepositoryImpl(getPreferredCurrencyApi)
    }

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
}
