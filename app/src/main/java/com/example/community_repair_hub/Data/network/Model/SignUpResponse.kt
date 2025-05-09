package com.example.community_repair_hub.data.network.model

import com.example.community_repair_hub.data.network.model.UserData


/**
 * Data class representing the expected response from the signup API endpoint.
 */
data class SignupResponse(
    val success: Boolean = false,
    val message: String = "",
    val token: String? = null,
    val user: UserData? = null
)
