package com.example.grocery_shop.api.client

import com.example.grocery_shop.api.base.BaseHttpClient
import com.example.grocery_shop.util.Constants
import com.example.grocery_shop.util.UserManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class ApiClient : BaseHttpClient() {

    private val token by lazy {
//        UserManager.getToken(MyApplication.APP_CONTEXT)
        "abcxyz"
    }

    companion object {
        @Volatile
        private var instance: ApiClient? = null

        @JvmStatic
        private fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ApiClient().also {
                    instance = it
                }
            }

        fun <T> createService(service: Class<T>): T {
            return getInstance().create(service)
        }
    }

    override fun getBaseUrl(): String {
        return Constants.LIVE_BASE_URL
    }

    override fun handleOkBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.header("Token", token)
            }
            chain.proceed(requestBuilder.build())
        })
    }

    override fun handleRetrofitBuilder(builder: Retrofit.Builder) {

    }
}