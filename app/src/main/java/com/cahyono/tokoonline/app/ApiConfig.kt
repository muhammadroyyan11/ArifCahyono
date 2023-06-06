package com.cahyono.tokoonline.app

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    private const val IS_PRODUCTION = false
    private val client: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://192.168.1.13/TokoOnlineWebMaster/public/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

    val instanceRetrofit: ApiService
        get() = client.create(ApiService::class.java)
}