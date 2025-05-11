package com.example.community_repair_hub.data.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Use your computer's IP address instead of localhost
    private const val BASE_URL = "http://10.5.199.23:5500/" // Replace with your computer's IP address
    // private const val BASE_URL = "http://10.0.2.2:5500/" // For Android Emulator
    // private const val BASE_URL = "http://localhost:5500/" // For local testing

    val instance: ApiService by lazy {
        Log.d("RetrofitClient", "Initializing Retrofit with BASE_URL: $BASE_URL")
        
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("RetrofitClient", "Network: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}