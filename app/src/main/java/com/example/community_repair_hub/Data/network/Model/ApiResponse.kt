package com.example.community_repair_hub.data.network.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
) 