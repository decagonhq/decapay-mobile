package com.decagonhq.decapay.feature.edit_profile.di

import com.decagonhq.decapay.feature.edit_profile.data.network.api.EditProfileApi
import com.decagonhq.decapay.feature.edit_profile.data.repository.EditProfileRepositoryImpl
import com.decagonhq.decapay.feature.edit_profile.domain.repository.EditProfileRepository
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
object EditRequestModule {

    @Provides
    @Singleton
    fun provideEditProfileApi(retrofit: Retrofit): EditProfileApi {
        return retrofit.create(EditProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRepository(editProfileApi: EditProfileApi): EditProfileRepository {
        return EditProfileRepositoryImpl(editProfileApi)
    }
}