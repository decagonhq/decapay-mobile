package com.decagonhq.decapay.feature.userprofile.di

import com.decagonhq.decapay.feature.userprofile.data.network.api.UserProfileApi
import com.decagonhq.decapay.feature.userprofile.data.repository.UserProfileRepositoryImpl
import com.decagonhq.decapay.feature.userprofile.domain.repository.UserProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserProfileNetworkModule {

    @Provides
    @Singleton
    fun provideUserProfileApi(retrofit: Retrofit): UserProfileApi {
        return retrofit.create(UserProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserProfileRepository(userProfileApi: UserProfileApi): UserProfileRepository {
        return  UserProfileRepositoryImpl(userProfileApi)
    }
}