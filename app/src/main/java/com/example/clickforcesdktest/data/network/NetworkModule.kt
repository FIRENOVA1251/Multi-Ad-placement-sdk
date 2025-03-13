package com.example.clickforcesdktest.data.network

import com.example.clickforcesdktest.data.model.BannerContent
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Retrofit 設定
object NetworkModule {

    private const val BASE_URL = "https://ad.holmesmind.com"

    // 自訂 Gson 設定，包含 `BannerContentDeserializer`
    private val gson = GsonBuilder()
        .registerTypeAdapter(BannerContent::class.java, BannerContentDeserializer()) // 註冊自訂的 JSON 解析器
        .create()

    // 若需要攔截器，超時設定或其他設定，可在此加上
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // 暴露給外部的 API 介面實例
    val adApiService: AdApiService by lazy {
        retrofit.create(AdApiService::class.java)
    }
}