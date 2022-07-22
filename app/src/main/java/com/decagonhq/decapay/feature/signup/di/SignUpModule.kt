package com.decagonhq.decapay.feature.signup.di

import com.decagonhq.decapay.feature.signup.data.network.api.SignUpApi
import com.decagonhq.decapay.feature.signup.data.repository.SignUpRepositoryImpl
import com.decagonhq.decapay.feature.signup.domain.repository.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SignUpModule {



    @Provides
    @Singleton
    fun provideSignUpApi(retrofit: Retrofit): SignUpApi {
        return retrofit.create(SignUpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(signUpApi: SignUpApi): SignUpRepository {
        return SignUpRepositoryImpl(signUpApi)
    }
}