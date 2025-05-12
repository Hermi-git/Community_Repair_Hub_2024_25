package com.example.community_repair_hub.data.network.model

data class IssueResponse(
    val _id: String? = null,
    val category: String? = null,
    val locations: Locations? = null,
    val description: String? = null,
    val issueDate: String? = null,
    val imageURL: String? = null,
    val status: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class Locations(
    val city: String? = null,
    val specificArea: String? = null
)