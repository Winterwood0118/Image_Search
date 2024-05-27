package com.example.imagesearch.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK 9982187f033a2c4ec2ff283fb4ac687c"
            )
            .build()
        return chain.proceed(newRequest)
    }
}