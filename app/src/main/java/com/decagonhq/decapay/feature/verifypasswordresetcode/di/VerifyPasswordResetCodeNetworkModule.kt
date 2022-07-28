package com.decagonhq.decapay.feature.verifypasswordresetcode.di

import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.api.VerifyPasswordResetCodeApi
import com.decagonhq.decapay.feature.verifypasswordresetcode.data.network.repository.VerifyPasswordResetCodeRepositoryImpl
import com.decagonhq.decapay.feature.verifypasswordresetcode.domain.repository.VerifyPasswordResetCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VerifyPasswordResetCodeNetworkModule {

    @Provides
    @Singleton
    fun provideVerifyPasswordResetCodeApi(retrofit: Retrofit): VerifyPasswordResetCodeApi {
        return retrofit.create(VerifyPasswordResetCodeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVerifyPasswordResetRepository(verifyPasswordResetCodeApi: VerifyPasswordResetCodeApi): VerifyPasswordResetCodeRepository {
        return VerifyPasswordResetCodeRepositoryImpl(verifyPasswordResetCodeApi)
    }
}
