package com.example.community_repair_hub.data.network.model

/**
 * Data class representing the request body sent to the signup API endpoint.
 */
data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val region: String,
    val city: String
)
