package com.example.ftblappfinal.util

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("key", API_KEY)
            .addHeader("secret", SECRET_KEY)
            .build()
        return chain.proceed(request)
    }

}