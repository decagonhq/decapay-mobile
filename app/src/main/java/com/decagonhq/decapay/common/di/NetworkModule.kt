package com.decagonhq.decapay.common.di

import android.content.Context
import com.decagonhq.decapay.common.constants.NetworkConstant
import com.decagonhq.decapay.common.data.model.HeaderInterceptor
import com.decagonhq.decapay.common.data.sharedpreference.DecapayPreferences
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.errorhelper.ExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * provide the logger
     * this logs out every response to and from the web service
     */
    @Provides
    @Singleton
    fun providesLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    /**
     * provide the logger
     * this logs out every response to and from the web service
     */
    @Provides
    @Singleton
    fun providesMobileHeaderInterceptor(preferences: Preferences): HeaderInterceptor {
        return HeaderInterceptor(preferences)
    }

    /**
     * provide gsonconverter
     */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * provide the OkHttp
     */
    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        preferences: Preferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    /**
     * it provides retrofit http client
     * for available endpoints to use
     */
    @Provides
    @Singleton
    fun providesRetrofitHttpClientForEndpointsToUse(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    /**
     * provide sharedPreference
     */
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): Preferences {
        return DecapayPreferences(context)
    }

    /**
     * provide error handler
     */
    @Provides
    @Singleton
    fun provideErrorHandle(@ApplicationContext context: Context): ExceptionHandler {
        return ExceptionHandler(context)
    }
}
