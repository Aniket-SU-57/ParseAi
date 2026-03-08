package com.adityaproj.parseai.network

import com.adityaproj.parseai.Auth.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val token = runBlocking {

            tokenManager.getToken()


        }

        val newRequest = request.newBuilder()

        token?.let {

            newRequest.addHeader(
                "Authorization",
                "Bearer $it"
            )

        }

        return chain.proceed(newRequest.build())
    }
}