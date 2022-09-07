package com.decagonhq.decapay.feature.usersettings.di

import com.decagonhq.decapay.feature.usersettings.data.network.api.GetLocationListApi
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
}
