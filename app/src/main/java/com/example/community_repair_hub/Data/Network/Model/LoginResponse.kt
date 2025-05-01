package com.example.community_repair_hub.data.network.model

/**
 * Data class representing the expected response from the login API endpoint.
 */
data class LoginResponse(
    val success: Boolean, // Indicates if the login was successful
    val message: String,  // A message from the backend (e.g., "Login successful" or error details)
    // Optional: Include user data or authentication token returned upon successful login
    // val user: User? = null, // Example: nested User data class
    val token: String? = null // Example: Authentication token
)

// Example nested User data class (if needed)
// data class User(
//     val id: String,
//     val name: String,
//     val email: String,
//     val role: String
// )
