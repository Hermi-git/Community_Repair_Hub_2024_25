package com.example.community_repair_hub.data.network.model

/**
 * Data class representing the expected response from the signup API endpoint.
 */
data class SignupResponse(
    val success: Boolean, // Indicates if the signup was successful on the backend
    val message: String,  // A message from the backend (e.g., "Signup successful" or error details)
    // Optional: Include other relevant data returned by the backend, like a user ID or token
    // val userId: String? = null,
    val token: String? = null,
    val user:SignupRequest,
)
