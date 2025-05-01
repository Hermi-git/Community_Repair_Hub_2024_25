package com.example.community_repair_hub.data.network

import com.example.community_repair_hub.data.network.model.LoginRequest
import com.example.community_repair_hub.data.network.model.LoginResponse
import com.example.community_repair_hub.data.network.model.SignupRequest
import com.example.community_repair_hub.data.network.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API interface defining the network endpoints.
 */
interface ApiService {

    /**
     * Sends signup data to the backend.
     * @param request The signup request body containing user details.
     * @return A SignupResponse indicating success or failure.
     */
    @POST("api/signup") // Adjust this path to match your actual Express.js API route
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    /**
     * Sends login credentials to the backend.
     * @param request The login request body containing email and password.
     * @return A LoginResponse indicating success or failure, potentially with a token.
     */
    @POST("api/login") // Adjust this path to match your actual Express.js API route
    suspend fun login(@Body request: LoginRequest): LoginResponse

    // Add other API endpoints here as needed
    // Example:
    // @GET("api/users/{userId}")
    // suspend fun getUserProfile(@Path("userId") userId: String): UserProfileResponse
}
