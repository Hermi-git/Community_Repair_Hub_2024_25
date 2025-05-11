package com.example.community_repair_hub.Data.network.Model

data class RegionCity(
    val region: String,
    val cities: List<String>
)

data class RegionCityResponse(
    val regions: Map<String, List<String>>
)