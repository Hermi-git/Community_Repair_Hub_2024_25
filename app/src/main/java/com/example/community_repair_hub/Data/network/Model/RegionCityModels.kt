package com.example.community_repair_hub.data.network.model

data class RegionCityResponse(
    val success: Boolean = true,
    val message: String? = null,
    val data: Map<String, List<String>> = emptyMap()
) 