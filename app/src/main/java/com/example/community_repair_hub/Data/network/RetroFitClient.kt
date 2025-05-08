package com.example.community_repair_hub.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Singleton object to provide a configured Retrofit instance.
 */
object RetrofitClient {

    // Replace with your actual backend base URL
    // For local development with emulator: "http://10.0.2.2:YOUR_PORT/"
    // For physical device on same network: "http://YOUR_COMPUTER_IP:YOUR_PORT/"
    const val BASE_URL = "http://192.168.199.84:5500/" // Replace with your actual IP
    // Example: Replace with your Express server address

    // Configure OkHttpClient (optional, but good for setting timeouts, interceptors, etc.)
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
        .build()

    // Lazy-initialized Retrofit instance
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the configured OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()

        retrofit.create(ApiService::class.java)
    }

    // You can add other service instances here if you have multiple API interfaces
    // val otherService: OtherApiService by lazy { ... }
}
