package com.github.alvarosct02.criptocurrency.data.source.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitApiClient(
    private val baseUrl: String
) {

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor {chain ->
                val requestBuilder = chain.request().newBuilder()
                val userAgent = System.getProperty("http.agent")?: DEFAULT_USER_AGENT
                requestBuilder.header("User-Agent", userAgent)
                chain.proceed(requestBuilder.build())
            }
            .readTimeout(TIMEOUT_API_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_API_SECONDS, TimeUnit.SECONDS)
        return builder.build()
    }

    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val TIMEOUT_API_SECONDS = 15L
        const val DEFAULT_USER_AGENT = "Android-Device"
    }
}