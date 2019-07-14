package com.testexample.componentExample.data.remote

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * This okhttp interceptor is responsible for adding the common query parameters and headers
 * for every service calls
 */
class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val url = originalHttpUrl.newBuilder()
                .build()

        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}