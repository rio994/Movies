package com.levelup.movies.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()



        val url = request
            .url()
            .newBuilder()
            .addQueryParameter("api_key", "fe3b8cf16d78a0e23f0c509d8c37caad")
            .build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}