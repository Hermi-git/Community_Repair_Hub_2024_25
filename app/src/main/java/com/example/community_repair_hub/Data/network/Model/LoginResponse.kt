package com.example.community_repair_hub.data.network.model

import com.example.community_repair_hub.data.network.model.UserData

/**
 * Data class representing the expected response from the login API endpoint.
 */
data class LoginResponse(
    val success: Boolean = false,
    val message: String = "",
    val token: String? = null,
    val user: UserData? = null

)

// Example nested User data class (if needed)
// data class User(
//     val id: String,
//     val name: String,
//     val email: String,
//     val role: String
// )
