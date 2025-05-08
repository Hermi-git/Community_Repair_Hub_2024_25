package com.example.community_repair_hub.Data.Network.Model

data class IssueResponse(
    val _id: String,
    val category: String?,
    val locations: Locations?,
    val Description: String?,
    val Date: String?,
    val Status: String,
    val ImagURL: String?,
    val createdAt: String?,
    val updatedAt: String?
)

data class Locations(
    val city: String,
    val specficArea: String
)