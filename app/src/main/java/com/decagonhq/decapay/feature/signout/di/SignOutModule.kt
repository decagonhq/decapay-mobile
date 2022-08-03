package com.decagonhq.decapay.feature.signout.di

import com.decagonhq.decapay.feature.signout.data.network.api.SignOutApi
import com.decagonhq.decapay.feature.signout.data.repository.SignOutRepositoryImpl
import com.decagonhq.decapay.feature.signout.domain.repository.SignOutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignOutModule {

    @Provides
    @Singleton
    fun provideSignOutApi(retrofit: Retrofit): SignOutApi {
        return retrofit.create(SignOutApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(signOutApi: SignOutApi): SignOutRepository {
        return SignOutRepositoryImpl(signOutApi)
    }
}
