package br.com.dcarv.criticalchallenge.common.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    @NewsApiServiceApiKey private val apiKey: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        return chain.proceed(newRequest)
    }
}
