package com.decagonhq.decapay.common.data.model

import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val preferences: Preferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            chain.request()
                .newBuilder()
                .addHeader("DVC_KY_HDR", "1")
                .addHeader("Authorization", "Bearer ${preferences.getToken()}")
                .build()
        )
    }
}
