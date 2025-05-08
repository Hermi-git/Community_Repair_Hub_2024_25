package com.example.community_repair_hub.data.network.model

/**
 * Data class representing the request body sent to the login API endpoint.
 */
data class LoginRequest(
    val email: String,
    val password: String
)
