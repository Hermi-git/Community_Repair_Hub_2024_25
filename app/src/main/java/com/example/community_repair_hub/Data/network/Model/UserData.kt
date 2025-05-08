package com.example.community_repair_hub.data.network.model

data class UserData(
    val _id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val imageUrl: String? = null,
    val region: String = "",
    val city: String = ""
)
