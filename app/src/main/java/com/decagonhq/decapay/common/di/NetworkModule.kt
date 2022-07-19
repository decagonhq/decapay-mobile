package com.decagonhq.decapay.common.di

import com.decagonhq.decapay.common.constants.NetworkConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    /**
     * it provides retrofit http client
     * for available endpoints to use
     */
    @Provides
    @Singleton
    fun providesRetrofitHttpClientForEndpointsToUse(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}
