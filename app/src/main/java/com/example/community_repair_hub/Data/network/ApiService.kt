package com.example.community_repair_hub.data.network

import com.example.community_repair_hub.data.network.model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {
    @Multipart
    @POST("users/register")
    suspend fun signup(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("role") role: RequestBody,
        @Part("region") region: RequestBody,
        @Part("city") city: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Response<SignupResponse>

    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @GET("/users/profile")
    suspend fun getProfile(): Response<UserData>
    @POST("users/logout")
    suspend fun logout(): Response<LogoutResponse>

    @POST("users/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("users/reset-password/{token}")
    suspend fun resetPassword(
        @Path("token") token: String,
        @Body request: ResetPasswordRequest
    ): Response<ResetPasswordResponse>

    @Multipart
    @POST("api/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ResponseBody>



    // Citizen endpoints
    @GET("citizens/issues")
    suspend fun getIssues(): ApiResponse<List<IssueResponse>>

    @GET("issues/category")
    suspend fun searchByCategory(@Query("category") category: String): ApiResponse<List<IssueResponse>>

    @GET("issues/location")
    suspend fun searchByLocation(@Query("location") location: String): ApiResponse<List<IssueResponse>>


    @Multipart
    @POST("citizens/report")
    suspend fun reportIssue(
        @Part image: MultipartBody.Part,
        @Part("category") category: RequestBody,
        @Part("city") city: RequestBody,
        @Part("specificAddress") specificAddress: RequestBody,
        @Part("description") description: RequestBody,
        @Part("issueDate") issueDate: RequestBody
    ): Response<JsonObject>


    @POST("report")
    suspend fun reportIssue(@Body issue: Map<String, String>): ApiResponse<Map<String, Any>>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000/" // For Android Emulator
        // private const val BASE_URL = "http://localhost:3000/" // For local testing

        fun create(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}