package com.decagonhq.decapay.common.data.model

import okhttp3.Interceptor
import retrofit2.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.run {
        proceed(
            request()
                .newBuilder()
              //  .addHeader("appid", "hello")
                .build()
        )
    }
}