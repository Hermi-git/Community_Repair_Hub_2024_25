package com.example.community_repair_hub.data.network.model

data class IssueResponse(
    val _id: String? = null,
    val category: String? = null,
    val locations: Locations? = null,
    val Description: String? = null,
    val Date: String? = null,
    val Status: String? = null,
    val ImagURL: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class Locations(
    val city: String? = null,
    val specficArea: String? = null

)