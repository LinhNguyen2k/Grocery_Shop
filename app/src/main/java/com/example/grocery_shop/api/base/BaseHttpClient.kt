package com.example.grocery_shop.api.base

import com.example.grocery_shop.base.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseHttpClient {
    companion object {
        private const val DEFAULT_CONNECT_TIME = 100L
    }

    init {
        buildOkHttpClient()
    }

    fun <T> create(service: Class<T>): T {
        return buildRetrofit().create(service)
    }

    private fun buildRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val builder = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(buildOkHttpClient())
            .addConverterFactory(getConverterFactory() ?: GsonConverterFactory.create(gson))
        handleRetrofitBuilder(builder)
        return builder.build()
    }

    protected open fun getConverterFactory(): Converter.Factory? {
        return null
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Cache-Control", "no-cache")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Player-Agent", "okhttp/3.11.0")
                .build()
            chain.proceed(request)
        }
        val defaultClientBuilder =
            OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .also { builder ->
                    handleOkBuilder(builder)
                    if (BuildConfig.DEBUG) {
                        builder.addInterceptor(loggingInterceptor)
                    }
                }
        return defaultClientBuilder.build()
    }

    abstract fun getBaseUrl(): String

    abstract fun handleOkBuilder(builder: OkHttpClient.Builder)

    abstract fun handleRetrofitBuilder(builder: Retrofit.Builder)
}