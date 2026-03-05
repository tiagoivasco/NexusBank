package com.nexus.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Attaches the Authorization header to every outgoing request.
 * Token is set after login via [setToken] and cleared via [clearToken].
 *
 * Registered as a singleton in [NetworkModule] so the same instance is shared
 * across the entire app lifecycle.
 */
class AuthInterceptor : Interceptor {

    @Volatile
    private var token: String? = null

    fun setToken(newToken: String) {
        token = newToken
    }

    fun clearToken() {
        token = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = token?.let { bearerToken ->
            original.newBuilder()
                .header("Authorization", "Bearer $bearerToken")
                .header("Accept", "application/json")
                .build()
        } ?: original

        return chain.proceed(request)
    }
}
