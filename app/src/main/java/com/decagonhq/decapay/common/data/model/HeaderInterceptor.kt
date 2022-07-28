package com.decagonhq.decapay.common.data.model

import okhttp3.Interceptor

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.run {
        proceed(
            request()
                .newBuilder().addHeader("DVC_KY_HDR", "1")
                .build()
        )
    }
}
